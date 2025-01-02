package modelDAO;

import model.Seat;
import utility.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatDAO {
    private DatabaseConnector dbConnector;

    public SeatDAO(DatabaseConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    // getSeatByShowtimeId
    public List<Seat> getSeatByShowtimeId(String showtimeId) {
        List<Seat> seatList = new ArrayList<>();
        String sql = "SELECT seat.seatId,seat.`row`,seat.col,seat.seatType,seat.status\n" +
                "FROM showtime\n" +
                "JOIN seat ON showtime.showtimeId = seat.showtimeId\n" +
                "WHERE showtime.showtimeId = ?\n" +
                "ORDER BY seat.`row`,seat.col;\n";
        try {
            Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,showtimeId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String seatId = rs.getString("seatId");
                String row = rs.getString("row");
                Integer col = rs.getInt("col");
                String seatType = rs.getString("seatType");
                String status = rs.getString("status");

                seatList.add(new Seat(showtimeId,seatId,row,col,seatType,status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seatList;
    }

    // updateSeatStatus
    public boolean updateSeatStatus(String[] seatIds) {
        boolean isUpdated = false;
        String sql = "UPDATE seat SET status = 'notActivate' WHERE seatId = ?";
        try {
            Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            for (String seatId : seatIds) {
                pstmt.setString(1, seatId);
                int rowsAffected = pstmt.executeUpdate();
                if(rowsAffected>0) {
                    isUpdated = true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isUpdated;
    }

}
