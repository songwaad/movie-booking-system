/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cinema;
import modelDAO.CinemaDAO;
import modelDAO.TicketDAO;
import utility.DatabaseConnector;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "Ticket", urlPatterns = {"/api/ticket"})
public class Ticket extends HttpServlet {

    private DatabaseConnector dbConnector = new DatabaseConnector();
    private TicketDAO ticketDAO = new TicketDAO(dbConnector);



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        response.setContentType("application/json");

        String userId = (String) session.getAttribute("userId");
        List<model.Ticket> tickets = ticketDAO.getTicketByUserId(userId);
        
        if(tickets.size() >= 1) {
            Gson gson = new Gson();
            response.getWriter().write(gson.toJson(tickets));
            response.setStatus(HttpServletResponse.SC_OK); // 200 OK
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("{\"error\": \"Ticket not found\"}");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
