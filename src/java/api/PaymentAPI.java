/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Payment;
import modelDAO.PaymentDAO;
import utility.DatabaseConnector;

/**
 *
 * @author LENOVO
 */
@WebServlet(name = "PaymentAPI", urlPatterns = {"/api/payment/*"})
public class PaymentAPI extends HttpServlet {

    private DatabaseConnector dbConnector = new DatabaseConnector();
    private PaymentDAO paymentDAO = new PaymentDAO(dbConnector);


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        Payment payment = gson.fromJson(reader, Payment.class);

        try (PrintWriter out = response.getWriter()) {
            if(paymentDAO.addPayment(payment)) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.setContentType("application/json");

                out.write("{\"message\": \"Success to add Payment\",}");
                out.flush();
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                // สร้างข้อความ JSON สำหรับการตอบกลับเมื่อมีข้อผิดพลาด
                response.setContentType("application/json");
                out.write("{\"message\": \"Failed to add Payment\"}");
                out.flush();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();


        if(pathInfo==null || pathInfo.equals("/")) {
            List<Payment> paymentList = paymentDAO.getAllPayment();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            Gson gson = new Gson();
            response.getWriter().write(gson.toJson(paymentList));
            response.setStatus(HttpServletResponse.SC_OK); // 200 OK
        }
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
