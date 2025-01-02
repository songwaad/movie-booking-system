/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;

import com.sun.net.httpserver.HttpServer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.Date;
import java.util.List;
import model.Movie;
import modelDAO.MovieDAO;
import utility.DatabaseConnector;

@WebServlet("/api/movie/*")
public class MoviesAPI extends HttpServlet {

    private DatabaseConnector dbConnector = new DatabaseConnector();
    private MovieDAO movieDAO = new MovieDAO(dbConnector);
    private List<Movie> movies;

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            movies = movieDAO.getAllMovie();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            Gson gson = new Gson();
            response.getWriter().write(gson.toJson(movies));
            response.setStatus(HttpServletResponse.SC_OK); // 200 OK

        } else {
            String movieId = pathInfo.substring(1);

            Movie movie = movieDAO.getMovieById(movieId);

            if (movie.getMovieId() != null) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                Gson gson = new Gson();
                response.getWriter().write(gson.toJson(movie));
                response.setStatus(HttpServletResponse.SC_OK); // 200 OK

            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Movie not found\"}");
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String methodOverride = request.getHeader("X-HTTP-Method-Override");

        if ("PATCH".equalsIgnoreCase(methodOverride)) {
            BufferedReader reader = request.getReader();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            Movie partialMovie = gson.fromJson(reader, Movie.class);
            String movieId = request.getPathInfo().substring(1);
            boolean updated = movieDAO.updatePartialMovie(movieId, partialMovie);

            if (updated) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Movie updated successfully");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Failed to update movie");
            }

        } else {
            BufferedReader reader = request.getReader();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            Movie movie = gson.fromJson(reader, Movie.class);

            // เพิ่มการตรวจสอบว่ามีการบันทึกข้อมูลสำเร็จหรือไม่
            if (movieDAO.addMovie(movie)) {  // สมมติว่า addMovie คืนค่า true/false ตามการบันทึกสำเร็จ
                response.setStatus(HttpServletResponse.SC_CREATED);

                // สร้างข้อความ JSON สำหรับการตอบกลับ
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.write("{\"message\": \"Success\"}");
                out.flush();
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                // สร้างข้อความ JSON สำหรับการตอบกลับเมื่อมีข้อผิดพลาด
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.write("{\"message\": \"Failed to add movie\"}");
                out.flush();
            }
        }

    }

    // DELETE
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String movieId = request.getPathInfo().substring(1);
        if (movieDAO.deleteMovie(movieId)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 No Content
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
        }
    }

    // PUT
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String movieId = request.getPathInfo().substring(1);
        BufferedReader reader = request.getReader();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Movie updatedMovie = gson.fromJson(reader, Movie.class);

        updatedMovie.setMovieId(movieId);
        boolean success = movieDAO.updateMovie(updatedMovie);

        if (success) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Movie updated successfully");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Movie not found");
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
