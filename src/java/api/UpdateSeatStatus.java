/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package api;

import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.lang.ProcessBuilder.Redirect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import modelDAO.SeatDAO;
import utility.DatabaseConnector;


@WebServlet(name = "UpdateSeatStatus", urlPatterns = {"/api/updateSeatStatus"})
public class UpdateSeatStatus extends HttpServlet {
    private DatabaseConnector dbConnector = new DatabaseConnector();
    private SeatDAO seatDAO = new SeatDAO(dbConnector);


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();

        Map<String, Object> jsonMap = gson.fromJson(reader, Map.class);
        String[] seatIds = gson.fromJson(gson.toJson(jsonMap.get("seatIds")), String[].class);

        boolean isUpdated = seatDAO.updateSeatStatus(seatIds);
        try(PrintWriter out = response.getWriter()) {
            if(isUpdated) {
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_OK); // 200 OK
                out.write("{\"message\": \"Success\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"message\": \"Failed to updated Seat Status\"}");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
