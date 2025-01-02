package modelDAO;

import static jakarta.xml.bind.DatatypeConverter.parseTime;
import model.Cinema;
import utility.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CinemaDAO {

    private DatabaseConnector dbConnector;

    public CinemaDAO(DatabaseConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    //GET_ALL
    public List<Cinema> getAllCinema() {
        List<Cinema> cinemas = new ArrayList<>();
        try {
            Connection conn = dbConnector.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM cinema");

            while (rs.next()) {
                String cinemaId = rs.getString("cinemaId");
                String name = rs.getString("name");
                Time openTime = rs.getTime("openTime");
                Time closeTime = rs.getTime("closeTime");
                String contactNumber = rs.getString("contactNumber");
                String imageUrl = rs.getString("imageUrl");
                String address = rs.getString("address");

                cinemas.add(new Cinema(cinemaId,name,openTime,closeTime,contactNumber,imageUrl, address));

                System.out.println("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cinemas;
    }

    // ADD
    public Boolean addCinema(Cinema cinema) {
        String sql = "INSERT INTO cinema (cinemaId,name,openTime,closeTime,contactNumber,imageUrl,address) VALUES(?,?,?,?,?,?,?)";
        try {
            Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, cinema.getCinemaId());
            pstmt.setString(2, cinema.getName());
            pstmt.setTime(3,cinema.getOpenTime());
            pstmt.setTime(4,cinema.getCloseTime());
            pstmt.setString(5,cinema.getContactNumber());
            pstmt.setString(6, cinema.getImageUrl());
            pstmt.setString(7,cinema.getAddress());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // GET_BY_ID
    public Cinema getCinemaById(String cinemaId) {
        Cinema cinema = new Cinema();
        String sql = "SELECT * FROM cinema WHERE cinemaId Like ?";

        try {
            Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,cinemaId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                cinema.setCinemaId(rs.getString("cinemaId"));
                cinema.setName(rs.getString("name"));
                cinema.setOpenTime(rs.getTime("openTime"));
                cinema.setCloseTime(rs.getTime("closeTime"));
                cinema.setContactNumber(rs.getString("contactNumber"));
                cinema.setImageUrl(rs.getString("imageUrl"));
                cinema.setAddress(rs.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cinema;
    }

    // DELETE
    public Boolean deleteCinema(String cinemaId) {
        String sql = "DELETE FROM cinema WHERE cinemaId = ?";

        try {
            Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,cinemaId);
            int row = pstmt.executeUpdate();
            return row>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // PUT
    public Boolean updateCinema(Cinema cinema) {
        String sql = "UPDATE cinema SET name = ?, openTime = ?, closeTime = ?, contactNumber = ?, imageUrl = ?, address = ? WHERE cinemaId = ?";
        try {
            Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,cinema.getName());
            pstmt.setTime(2,cinema.getOpenTime());
            pstmt.setTime(3,cinema.getCloseTime());
            pstmt.setString(4,cinema.getContactNumber());
            pstmt.setString(5,cinema.getImageUrl());
            pstmt.setString(6,cinema.getAddress());
            pstmt.setString(7, cinema.getCinemaId());

            int row = pstmt.executeUpdate();
            return row>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // PATCH
    public boolean updatePartialCinema(String cinemaId, Cinema cinema) {
        StringBuilder sql = new StringBuilder("UPDATE cinema SET ");
        boolean hasPrevious = false;

        if(cinema.getName()!=null) {
            sql.append("name = ?");
            hasPrevious = true;
        }


        if(cinema.getOpenTime() != null) {
            if(hasPrevious) sql.append(", ");
            sql.append("openTime = ?");
            hasPrevious = true;
        }

        if(cinema.getCloseTime() != null) {
            if(hasPrevious) sql.append(", ");
            sql.append("closeTime = ?");
            hasPrevious = true;
        }

        if(cinema.getContactNumber() != null) {
            if(hasPrevious) sql.append(", ");
            sql.append("contractNumber = ?");
            hasPrevious = true;
        }

        if(cinema.getImageUrl() != null) {
            if(hasPrevious) sql.append(", ");
            sql.append("imageUrl = ?");
            hasPrevious = true;
        }

        if(cinema.getAddress() != null) {
            if(hasPrevious) sql.append(", ");
            sql.append("address = ?");
            hasPrevious = true;
        }

        sql.append(" WHERE cinemaId = ?");

        try {
            Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            int index=1;

            if(cinema.getName() != null) {
                pstmt.setString(index++, cinema.getName());
            }

            if(cinema.getOpenTime() != null) {
                pstmt.setTime(index++, cinema.getOpenTime());
            }

            if(cinema.getCloseTime() != null) {
                pstmt.setTime(index++, cinema.getCloseTime());
            }

            if(cinema.getContactNumber() != null) {
                pstmt.setString(index++, cinema.getContactNumber());
            }

            if(cinema.getImageUrl() != null) {
                pstmt.setString(index++, cinema.getImageUrl());
            }

            if(cinema.getAddress() != null) {
                pstmt.setString(index++, cinema.getAddress());
            }

            pstmt.setString(index++, cinema.getCinemaId());

            int row = pstmt.executeUpdate();
            return  row>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
