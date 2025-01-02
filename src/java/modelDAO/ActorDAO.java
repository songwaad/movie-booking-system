package modelDAO;

import model.Actor;
import utility.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActorDAO {
    private DatabaseConnector dbConnector;

    public ActorDAO(DatabaseConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    // GET_ALL
    public List<Actor> getAllActor() {
        List<Actor> actors = new ArrayList<>();
        try {
            Connection conn = dbConnector.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM actor");

            while (rs.next()) {
                String actorId = rs.getString("actorId");
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String imageUrl = rs.getString("imageUrl");

                actors.add(new Actor(actorId,fname,lname,imageUrl));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return actors;
    }

    // GET_ActorsByMovieId
    public List<Actor> getActorsByMovieId(String movieId) {
        List<Actor> actors = new ArrayList<>();
        String sql = "SELECT a.actorId, a.fname, a.lname, a.imageUrl\n" +
                "FROM actor a\n" +
                "JOIN movieActor ma ON a.actorId = ma.actorId\n" +
                "WHERE ma.movieId = ?";

        System.out.println("------- SQL = " + sql);
        try {
            Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, movieId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String actorId = rs.getString("actorId");
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String imageUrl = rs.getString("imageUrl");

                actors.add(new Actor(actorId,fname,lname,imageUrl));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("---- sql Actors Size = " + actors.size());
        return actors;
    }

    // ADD
    public Boolean addActor(Actor actor) {
        String sql = "INSERT INTO actor (actorId, fname, lname, imageUrl) VALUES(?,?,?,?)";
        try {
            Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, actor.getActorId());
            pstmt.setString(2, actor.getFname());
            pstmt.setString(3, actor.getLname());
            pstmt.setString(4, actor.getImageUrl());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // GET_BY_ID
    public Actor getActorById(String actorId) {
        Actor actor = new Actor();
        String sql = "SELECT * FROM actor WHERE actorId Like ?";

        try {
            Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,actorId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                actor.setActorId(rs.getString("actorId"));
                actor.setFname(rs.getString("fname"));
                actor.setLname(rs.getString("lname"));
                actor.setImageUrl(rs.getString("imageUrl"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actor;
    }

    // DELETE
    public Boolean deleteActor(String actorId) {
        String sql = "DELETE FROM actor WHERE actorId = ?";
        try {
            Connection connection = dbConnector.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1,actorId);
            int row = pstmt.executeUpdate();
            return row>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // PUT
    public Boolean updateActor(Actor actor) {
        String sql = "UPDATE actor SET fname = ?, lname = ?, imageUrl = ? WHERE actorId = ?";
        try {
            Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, actor.getFname());
            pstmt.setString(2, actor.getFname());
            pstmt.setString(3, actor.getImageUrl());
            pstmt.setString(4, actor.getActorId());

            int row = pstmt.executeUpdate();
            return row>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // PATCH

    public boolean updatePartialActor(String actorId, Actor actor) {
        StringBuilder sql = new StringBuilder("UPDATE actor SET ");
        boolean hasPrevious = false;
        

        if(actor.getFname() != null) {
            sql.append("fname = ?");
            hasPrevious = true;
        }

        if(actor.getLname() != null) {
            if(hasPrevious) sql.append(", ");
            sql.append("lname = ?");
            hasPrevious = true;
        }

        if(actor.getImageUrl()!=null) {
            if(hasPrevious) sql.append(", ");
            sql.append("imageUrl = ?");
            hasPrevious = true;
        }

        sql.append(" WHERE actorId = ?");

        try {
            Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            int index=1;
            if(actor.getFname() != null) {
                pstmt.setString(index++, actor.getFname());
            }
            if(actor.getLname() != null) {
                pstmt.setString(index++, actor.getLname());
            }
            if(actor.getImageUrl() != null) {
                pstmt.setString(index++, actor.getImageUrl());
            }
            pstmt.setString(index++, actorId);

            System.out.println("sql :"+sql);
            int row = pstmt.executeUpdate();
            System.out.println("row = " + row);
            return row > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No");
            return false;
        }
    }
}
