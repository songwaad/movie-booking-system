package modelDAO;

import com.google.gson.Gson;
import model.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utility.DatabaseConnector;

public class MovieDAO {

    private DatabaseConnector dbConnector;

    public MovieDAO(DatabaseConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    // GET_ALL
    public List<Movie> getAllMovie() {
        List<Movie> movie = new ArrayList<>();
        try (Connection conn = dbConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM movie")) {

            while (rs.next()) {
                String movieId = rs.getString("movieId");
                String title = rs.getString("title");
                Date releaseDate = rs.getDate("releaseDate");
                int duration = rs.getInt("duration");
                String synopsis = rs.getString("synopsis");
                String trailer = rs.getString("trailer");
                String imageUrl = rs.getString("imageUrl");

                movie.add(new Movie(movieId, title, releaseDate, duration, synopsis, trailer, imageUrl));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movie;
    }

    public List<Movie> getCurrentMovieShowing() {
        List<Movie> movies = new ArrayList<>();
        try {
            Connection conn = dbConnector.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT m.movieId, m.title, m.releaseDate, m.imageUrl\n" +
                    "FROM movie m\n" +
                    "JOIN showtime s ON m.movieId = s.movieId\n" +
                    "WHERE s.showDate >= DATE_SUB(CURRENT_DATE(), INTERVAL DAY(CURRENT_DATE()) - 1 DAY)\n" +
                    "  AND s.showDate < DATE_ADD(DATE_SUB(CURRENT_DATE(), INTERVAL DAY(CURRENT_DATE()) - 1 DAY), INTERVAL 1 MONTH);");

            while (rs.next()) {
                Movie movie = new Movie();
                movie.setMovieId(rs.getString("movieId"));
                movie.setTitle(rs.getString("title"));
                movie.setReleaseDate(rs.getDate("releaseDate"));
                movie.setImageUrl(rs.getString("imageUrl"));

                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }
    
    // getComingMovieShowing
    public List<Movie> getComingMovieShowing() {
        List<Movie> movies = new ArrayList<>();
        try {
            Connection conn = dbConnector.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT m.movieId, m.title, m.releaseDate, m.imageUrl\n" +
                    "FROM movie m\n" +
                    "JOIN showtime s ON m.movieId = s.movieId\n" +
                    "WHERE s.showDate >= DATE_ADD(LAST_DAY(CURRENT_DATE()), INTERVAL 1 DAY);");
            
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setMovieId(rs.getString("movieId"));
                movie.setTitle(rs.getString("title"));
                movie.setReleaseDate(rs.getDate("releaseDate"));
                movie.setImageUrl(rs.getString("imageUrl"));

                movies.add(movie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movies;
    }
    
    private String generateNewMovieId() {
        String newMovieId = "mov" + System.currentTimeMillis();
        return newMovieId;
    }

    // ADD
    public Boolean addMovie(Movie movie) {
        
        String movieId = generateNewMovieId();
        System.out.println("---------addMovie : " + movieId);
        String sql = "INSERT INTO movie (movieId, title, releaseDate, duration, synopsis, trailer, imageUrl) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, movieId);
            pstmt.setString(2, movie.getTitle());
            pstmt.setDate(3, new java.sql.Date(movie.getReleaseDate().getTime()));
            pstmt.setInt(4, movie.getDuration());
            pstmt.setString(5, movie.getSynopsis());
            pstmt.setString(6, movie.getTrailer());
            pstmt.setString(7, movie.getImageUrl());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    // GET_By_ID
    public Movie getMovieById(String movieId) {
        Movie movie = new Movie();
        String query = "SELECT * FROM movie WHERE movieId Like ?";

        try (Connection conn = dbConnector.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, movieId);
                ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                movie.setMovieId(rs.getString("movieId"));
                movie.setTitle(rs.getString("title"));
                movie.setReleaseDate(rs.getDate("releaseDate"));
                movie.setDuration(rs.getInt("duration"));
                movie.setSynopsis(rs.getString("synopsis"));
                movie.setTrailer(rs.getString("trailer"));
                movie.setImageUrl(rs.getString("imageUrl"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return movie;

    }

    // DELETE
    public Boolean deleteMovie(String movieId) {
        String sql = "DELETE FROM movie WHERE movieId = ?";

        try (Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1,movieId);

            int row = pstmt.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    // PUT
    public Boolean updateMovie(Movie movie) {
        String sql = "UPDATE movie SET title = ?, releaseDate = ?, duration = ?, synopsis = ?, trailer = ?, imageUrl = ? WHERE movieId = ?";

        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            pstmt.setString(1, movie.getTitle());
            pstmt.setDate(2, new java.sql.Date(movie.getReleaseDate().getTime()));
            pstmt.setInt(3,movie.getDuration());
            pstmt.setString(4,movie.getSynopsis());
            pstmt.setString(5,movie.getTrailer());
            pstmt.setString(6, movie.getImageUrl());
            pstmt.setString(7,movie.getMovieId());
            
            


            int row = pstmt.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    // PATCH
    public boolean updatePartialMovie(String movieId, Movie movie) {
        StringBuilder sql = new StringBuilder("UPDATE movie SET ");
        boolean hasPrevious = false;

        if (movie.getTitle() != null) {
            sql.append("title = ?");
            hasPrevious = true;
        }

        if(movie.getReleaseDate()!=null) {
            if(hasPrevious) sql.append(", ");
            sql.append("releaseDate = ?");
            hasPrevious = true;
        }

        if(movie.getDuration()!=null) {
            if(hasPrevious) sql.append(", ");
            sql.append("duration = ?");
            hasPrevious = true;
        }

        if(movie.getSynopsis()!=null) {
            if(hasPrevious) sql.append(", ");
            sql.append("synopsis = ?");
            hasPrevious = true;
        }

        if(movie.getTrailer()!=null) {
            if(hasPrevious) sql.append(", ");
            sql.append("trailer = ?");
            hasPrevious = true;
        }

        if(movie.getImageUrl()!=null) {
            if(hasPrevious) sql.append(", ");
            sql.append("imageUrl = ?");
            hasPrevious = true;
        }
        
        sql.append(" ");
        sql.append("WHERE movieId = ?");

        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int index = 1;

            if(movie.getTitle() != null) {
                pstmt.setString(index++, movie.getTitle());
            }

            if(movie.getReleaseDate() != null) {
                pstmt.setDate(index++, new java.sql.Date(movie.getReleaseDate().getTime()));
            }

            if(movie.getDuration() != null) {
                pstmt.setInt(index++, movie.getDuration());
            }

            if(movie.getSynopsis() != null) {
                pstmt.setString(index++, movie.getSynopsis());
            }

            if(movie.getTrailer() != null) {
                pstmt.setString(index++, movie.getTrailer());
            }

            if(movie.getImageUrl() != null) {
                pstmt.setString(index++, movie.getImageUrl());
            }

            pstmt.setString(index, movieId);

            int row = pstmt.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }

    }
}
