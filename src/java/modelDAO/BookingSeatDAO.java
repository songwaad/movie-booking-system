package modelDAO;

import model.BookingSeat;
import utility.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookingSeatDAO {
    private DatabaseConnector dbConnector;

    public BookingSeatDAO(DatabaseConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    // generateBookingSeat
    private  String generateBookingSeat() {
        int bookingSeatCount = getBookingSeatCount();
        return String.format("bkSeat%010d", bookingSeatCount + 1);
    }

    private int getBookingSeatCount() {
        String sql = "SELECT COUNT(*) FROM bookingseat";
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
    public boolean addBookingSeat(ArrayList<BookingSeat> bookingSeats) {
        int bookingSeatCount = getBookingSeatCount();
        String sql = "INSERT INTO bookingseat (bookingSeatId, bookingId, seatId) VALUES(?,?,?)";

        try {
            Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            for(BookingSeat bookingSeat : bookingSeats) {
                String bookingSeatId = String.format("bkSeat%010d", ++bookingSeatCount); // เพิ่มค่าในแต่ละรอบ
                pstmt.setString(1,bookingSeatId);
                pstmt.setString(2,bookingSeat.getBookingId());
                pstmt.setString(3,bookingSeat.getSeatId());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
