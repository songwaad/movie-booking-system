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
import model.Booking;
import modelDAO.BookingDAO;
import utility.DatabaseConnector;

@WebServlet(name = "BookingAPI", urlPatterns = {"/api/booking"})
public class BookingAPI extends HttpServlet {

    private DatabaseConnector dbConnector = new DatabaseConnector();
    private BookingDAO bookingDAO = new BookingDAO(dbConnector);


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        Gson gson = new Gson();
        Booking booking = gson.fromJson(reader, Booking.class);

        String bookingId = bookingDAO.addBooking(booking);

        try(PrintWriter out = response.getWriter()) {
            if(bookingId != null) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.setContentType("application/json");

                out.write("{\"message\": \"Success\", \"bookingId\": \"" + bookingId + "\"}");
                out.flush();

            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

                // สร้างข้อความ JSON สำหรับการตอบกลับเมื่อมีข้อผิดพลาด
                response.setContentType("application/json");
                out.write("{\"message\": \"Failed to add Booking\"}");
                out.flush();
            }
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
