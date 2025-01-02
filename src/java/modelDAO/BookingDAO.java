package modelDAO;

import model.Booking;
import utility.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookingDAO {
    private DatabaseConnector dbConnector;

    public BookingDAO(DatabaseConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    // generateBookingId
    private  String generateBookingId() {
        int bookingCount = getBookingCount();
        return  String.format("book%010d", bookingCount + 1);
    }

    // getBookingCount
    private int getBookingCount() {
        String sql = "SELECT COUNT(*) FROM booking";
        try {
            Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ADD
    public String addBooking(Booking booking) {

        String bookingId = generateBookingId();
        String sql = "INSERT INTO booking (bookingId, userId, showtimeId, status, totalAmount) VALUES(?,?,?,?,?)";

        try {
            Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,bookingId);
            pstmt.setString(2,booking.getUserId());
            pstmt.setString(3,booking.getShowtimeId());
            pstmt.setString(4,booking.getStatus());
            pstmt.setString(5,booking.getTotalAmount());

            pstmt.executeUpdate();
            return bookingId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
