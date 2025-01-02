/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalTime;
import model.Cinema;
import modelDAO.CinemaDAO;
import utility.DatabaseConnector;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "CinemaAPI", urlPatterns = {"/api/cinema/*"})
public class CinemaAPI extends HttpServlet {

    private DatabaseConnector dbConnector = new DatabaseConnector();
    private CinemaDAO cinemaDAO = new CinemaDAO(dbConnector);
    private List<Cinema> cinemas;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if(pathInfo == null || pathInfo.equals("/")) {
            cinemas = cinemaDAO.getAllCinema();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            Gson gson = new Gson();
            response.getWriter().write(gson.toJson(cinemas));
            response.setStatus(HttpServletResponse.SC_OK); // 200 OK
        } else {
            String cinemaId = pathInfo.substring(1);
            Cinema cinema = cinemaDAO.getCinemaById(cinemaId);

            if(cinema.getCinemaId() != null) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                Gson gson = new Gson();
                response.getWriter().write(gson.toJson(cinema));
                response.setStatus(HttpServletResponse.SC_OK); // 200 OK
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Cinema not found\"}");
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

        if("PATCH".equalsIgnoreCase(methodOverride)) {
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();
            Cinema partialCinema = gson.fromJson(reader, Cinema.class);
            String cinemaId = request.getPathInfo().substring(1);
            boolean updated = cinemaDAO.updatePartialCinema(cinemaId, partialCinema);

            if(updated) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Cinema updated successfully");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Failed to update cinema");
            }
        } else {
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();
            Cinema cinema = gson.fromJson(reader, Cinema.class);

            if(cinemaDAO.addCinema(cinema)) {
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
                out.write("{\"message\": \"Failed to add cinema\"}");
                out.flush();
            }
        }
    }

    // Delete
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cinemaId = request.getPathInfo().substring(1);
        if(cinemaDAO.deleteCinema(cinemaId)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 No Content
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
        }
    }

    // PUT
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cinemaId = request.getPathInfo().substring(1);
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        Cinema updatedCinema = gson.fromJson(reader,Cinema.class);

        updatedCinema.setCinemaId(cinemaId);
        boolean success = cinemaDAO.updateCinema(updatedCinema);

        if(success) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Cinema updated successfully");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Cinema not found");
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