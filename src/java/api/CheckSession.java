/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package api;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "CheckSession", urlPatterns = {"/api/checkSession"})
public class CheckSession extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        response.setContentType("application/json");

        try (PrintWriter out = response.getWriter()) {
            if (session != null && session.getAttribute("userId") != null) {
                String userId = (String) session.getAttribute("userId"); // ดึงข้อมูลชื่อผู้ใช้จาก session
                response.setStatus(HttpServletResponse.SC_OK);
                out.write("{\"loggedIn\": true, \"userId\": \"" + userId + "\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_OK);
                out.write("{\"loggedIn\": false}");
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
