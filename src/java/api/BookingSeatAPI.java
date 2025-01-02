/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package api;

import java.awt.print.Book;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.BookingSeat;
import modelDAO.BookingSeatDAO;
import utility.DatabaseConnector;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "BookingSeatAPI", urlPatterns = {"/api/bookingSeat"})
public class BookingSeatAPI extends HttpServlet {

    private DatabaseConnector dbConnector = new DatabaseConnector();
    private BookingSeatDAO bookingSeatDAO = new BookingSeatDAO(dbConnector);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();

        ArrayList<BookingSeat> bookingSeats = gson.fromJson(reader, new TypeToken<ArrayList<BookingSeat>>(){}.getType());

        try(PrintWriter out = response.getWriter()) {
            if(bookingSeatDAO.addBookingSeat(bookingSeats)) {
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_OK);
                out.write("{\"message\": \"Success\"}");
                out.flush();
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType("application/json");
                out.write("{\"message\": \"Failed to save seats.\"}");
                out.flush();
            }
        }

    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
