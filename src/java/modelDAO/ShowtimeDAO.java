package modelDAO;

import model.Showtime;
import utility.DatabaseConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShowtimeDAO {
    private DatabaseConnector dbConnector;

    public ShowtimeDAO(DatabaseConnector dbConnector) {this.dbConnector = dbConnector;}

    public List<Showtime> getShowtimeByMovieId(String movieId) {
        List<Showtime> showtimes = new ArrayList<>();
        String sql = "SELECT s.showtimeId, s.normalSeatPrice, s.vipSeatPrice, s.showTime, s.showDate, c.cinemaId, c.name, c.imageUrl\n" +
                "FROM showtime s\n" +
                "JOIN cinema c ON s.cinemaId = c.cinemaId\n" +
                "WHERE s.movieId = ? \n" +
                "ORDER BY s.showDate, c.cinemaId, s.showTime;";
        try {
            Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareCall(sql);
            pstmt.setString(1,movieId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String showtimeId = rs.getString("showtimeId");
                Float normalSeatPrice = rs.getFloat("normalSeatPrice");
                Float vipSeatPrice = rs.getFloat("vipSeatPrice");
                Time showTime = rs.getTime("showTime");
                Date showDate = rs.getDate("showDate");
                String cinemaId = rs.getString("cinemaId");
                String name = rs.getString("name");
                String imageUrl = rs.getString("imageUrl");

                showtimes.add(new Showtime(showtimeId,normalSeatPrice,vipSeatPrice,showTime,showDate,cinemaId,name,imageUrl));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return showtimes;
    }

    public Showtime getShowtimeByShowtimeId(String showtimeId) {
        Showtime showtime = new Showtime();
        String sql = "SELECT s.showtimeId, s.normalSeatPrice, s.vipSeatPrice, s.showTime, s.showDate, c.cinemaId, c.name, c.imageUrl\n" +
                "FROM showtime s\n" +
                "JOIN cinema c ON s.cinemaId = c.cinemaId\n" +
                "WHERE s.showtimeId = ? \n" +
                "ORDER BY s.showDate, c.cinemaId, s.showTime;";
        try {
            Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareCall(sql);
            pstmt.setString(1,showtimeId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                showtime.setShowtimeId(rs.getString("showtimeId"));
                showtime.setNormalSeatPrice(rs.getFloat("normalSeatPrice"));
                showtime.setVipSeatPrice(rs.getFloat("vipSeatPrice"));
                showtime.setShowTime(rs.getTime("showTime"));
                showtime.setShowDate(rs.getDate("showDate"));
                showtime.setCinemaId(rs.getString("cinemaId"));
                showtime.setName(rs.getString("name"));
                showtime.setImageUrl(rs.getString("imageUrl"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return showtime;
    }
}
