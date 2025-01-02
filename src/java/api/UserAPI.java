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

import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import modelDAO.UserDAO;
import utility.DatabaseConnector;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "UserAPI", urlPatterns = {"/api/user/*"})
public class UserAPI extends HttpServlet {

    private DatabaseConnector dbConnector = new DatabaseConnector();
    private UserDAO userDAO = new UserDAO(dbConnector);
    private List<User> users;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            users = userDAO.getAllUser();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            response.getWriter().write(gson.toJson(users));
            response.setStatus(HttpServletResponse.SC_OK); // 200 OK

        } else {
            String userId = pathInfo.substring(1);

            User user = userDAO.getUserById(userId);

            if (user.getUserId() != null) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                response.getWriter().write(gson.toJson(user));
                response.setStatus(HttpServletResponse.SC_OK); // 200 OK

            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"User not found\"}");
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String methodOverride = request.getHeader("X-HTTP-Method-Override");

        if ("PATCH".equalsIgnoreCase(methodOverride)) {
            processPatchRequest(request, response);
        } else {
            processPostRequest(request, response);
        }
    }

    // POST
    private void processPostRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
        String action = jsonObject.get("action").getAsString();

        try (PrintWriter out = response.getWriter()) {
            if ("register".equals(action)) {
                User user = gson.fromJson(jsonObject, User.class);
                user.setPassword(user.getPassword());
                if (userDAO.addUser(user)) {
                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    out.write("{\"message\": \"Success\"}");
                } else {
                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.write("{\"message\": \"Failed to add user\"}");
                }
            } else if ("login".equals(action)) {
                String email = jsonObject.get("email").getAsString();
                String password = jsonObject.get("password").getAsString();
                User user = new User();
                password = user.hashPassword(password);

                if (userDAO.validateLogin(email,password)) {

                    user = userDAO.getUserByEmail(email);

                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_OK);

                    JsonObject jsonResponse = new JsonObject();
                    jsonResponse.addProperty("message", "Login successful");
                    jsonResponse.addProperty("sessionId", request.getSession().getId());
                    jsonResponse.addProperty("email", user.getEmail());
                    jsonResponse.addProperty("fname", user.getFname());

                    System.out.println("JsonRespone = " + jsonResponse);

                    out.write(jsonResponse.toString());
                    out.flush();
                } else {
                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    out.write("{\"message\": \"Invalid username or password\"}");
                }

            }
        }

    }

    // PATCH
    private void processPatchRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        User partialUser = gson.fromJson(reader, User.class);

        String userId = request.getPathInfo();

        if (userId == null || userId.length() <= 1) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Invalid user ID\"}");
            return;
        }

        userId = userId.substring(1);
        boolean updated = userDAO.updatePartialUser(userId, partialUser);

        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            if (updated) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.write("{\"message\": \"User updated successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.write("{\"message\": \"Failed to update user\"}");
            }
        }
    }

    // Delete
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getPathInfo().substring(1);
        if (userDAO.deleteUser(userId)) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 No Content
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found
        }
    }

    // PUT
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getPathInfo().substring(1);
        BufferedReader reader = request.getReader();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        User updateUser = gson.fromJson(reader, User.class);

        updateUser.setUserId(userId);
        boolean success = userDAO.updateUser(updateUser);

        if (success) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("User updated successfully");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("User not found");
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
