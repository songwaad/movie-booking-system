package modelDAO;

import model.Ticket;
import utility.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TicketDAO {
    private DatabaseConnector dbConnector;

    public TicketDAO(DatabaseConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    // GET_BY_USER_ID
    public List<Ticket> getTicketByUserId (String userId) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT b.userId,b.bookingId, bs.bookingSeatId,seat.seatId, s.showDate, s.showTime, seat.row, seat.col,b.status,m.title AS movieTitle,c.name AS cinemaName\n" +
                "FROM booking b\n" +
                "JOIN showtime s ON b.showtimeId = s.showtimeId\n" +
                "JOIN movie m ON s.movieId = m.movieId\n" +
                "JOIN cinema c ON s.cinemaId = c.cinemaId\n" +
                "JOIN bookingSeat bs ON b.bookingId = bs.bookingId\n" +
                "JOIN seat ON seat.seatId = bs.seatId\n" +
                "WHERE b.userId = ?";
        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ) {

            pstmt.setString(1,userId);
            ResultSet rs = pstmt.executeQuery();
           
            while (rs.next()) {
                Ticket ticket = new Ticket();

                ticket.setUserId(rs.getString("userId"));
                ticket.setBookingId(rs.getString("bookingId"));
                ticket.setBookingSeatId(rs.getString("bookingSeatId"));
                ticket.setSeatId(rs.getString("seatId"));
                ticket.setShowDate(rs.getDate("showDate"));
                ticket.setShowTime(rs.getTime("showTime"));
                ticket.setRow(rs.getString("row"));
                ticket.setCol(rs.getInt("col"));
                ticket.setStatus(rs.getString("status"));
                ticket.setMovieTitle(rs.getString("movieTitle"));
                ticket.setCinemaName(rs.getString("cinemaName"));

                tickets.add(ticket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tickets;
    }
}
