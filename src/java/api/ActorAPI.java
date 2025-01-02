/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package api;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Actor;
import modelDAO.ActorDAO;
import utility.DatabaseConnector;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "ActorAPI", urlPatterns = {"/api/actor/*"})
public class ActorAPI extends HttpServlet {

    private DatabaseConnector dbConnector = new DatabaseConnector();
    private ActorDAO actorDAO = new ActorDAO(dbConnector);
    private List<Actor> actors;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

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
        String patchInfo = request.getPathInfo();

        if(patchInfo == null || patchInfo.equals("/")) {
            actors = actorDAO.getAllActor();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            Gson gson = new Gson();
            response.getWriter().write(gson.toJson(actors));
            response.setStatus(HttpServletResponse.SC_OK); // 200 OK
        } else {
            String actorId = patchInfo.substring(1);

            Actor actor = actorDAO.getActorById(actorId);

            if(actor.getActorId() != null) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                Gson gson = new Gson();
                response.getWriter().write(gson.toJson(actor));
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

        if("PATCH".equalsIgnoreCase(methodOverride)) {
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();
            Actor partialActor = gson.fromJson(reader, Actor.class);
            String actorId = request.getPathInfo().substring(1);
            boolean updated = actorDAO.updatePartialActor(actorId, partialActor);

            if(updated) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Actor updated successfully");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Failed to update actor");
            }
        } else {
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();
            Actor actor = gson.fromJson(reader, Actor.class);

            if(actorDAO.addActor(actor)) {
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
                out.write("{\"message\": \"Failed to add actor\"}");
                out.flush();
            }
        }
    }

    // DELETE
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actorId = request.getPathInfo().substring(1);
        if(actorDAO.deleteActor(actorId)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 No Content
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
        }
    }

    // PUT
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String actorId = request.getPathInfo().substring(1);
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        Actor updatedActor = gson.fromJson(reader, Actor.class);

        updatedActor.setActorId(actorId);
        boolean success = actorDAO.updateActor(updatedActor);

        if(success) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Actor updated successfully");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Actor not found");
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
