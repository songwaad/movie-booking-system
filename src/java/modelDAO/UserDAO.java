package modelDAO;

import model.User;
import utility.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private DatabaseConnector dbConnector;

    public UserDAO(DatabaseConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    // GET_ALL
    public List<User> getAllUser() {
        List<User> users = new ArrayList<>();
        try (Connection conn = dbConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT userId,fname,lname,email,password,role,regisDate,phoneNo FROM user")) {

            while (rs.next()) {
                String userId = rs.getString("userId");
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String role = rs.getString("role");
                Date regisDate = rs.getDate("regisDate");
                String phoneNo = rs.getString("phoneNo");
                
                users.add(new User(userId,fname,lname,email,password,role,regisDate,phoneNo));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    // Get_user_by_id
    public User getUserById(String userId) {
        User user = new User();
        String sql = "SELECT userId,fname,lname,email,role,regisDate,phoneNo FROM user WHERE userId = ?";
        try {
            Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,userId);
            ResultSet rs = pstmt.executeQuery();



            while (rs.next()) {
                user.setUserId(rs.getString("userId"));
                user.setFname(rs.getString("fname"));
                user.setLname(rs.getString("lname"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setRegisDate(rs.getDate("regisDate"));
                user.setPhoneNo(rs.getString("phoneNo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Get_user_by_email
    public User getUserByEmail(String email) {
        User user = new User();
        String sql = "SELECT userId,fname,lname,email,role,regisDate,phoneNo FROM user WHERE email = ?";
        try {
            Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,email);
            ResultSet rs = pstmt.executeQuery();



            while (rs.next()) {
                user.setUserId(rs.getString("userId"));
                user.setFname(rs.getString("fname"));
                user.setLname(rs.getString("lname"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setRegisDate(rs.getDate("regisDate"));
                user.setPhoneNo(rs.getString("phoneNo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    private int getUserCount() {
        String sql = "SELECT COUNT(*) FROM user";
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

    // generateUserId
    private  String generateUserId() {
        int userCount = getUserCount();
        return  String.format("cust%04d", userCount + 1);
    }

    // ADD
    public Boolean addUser(User user) {

        String userId = generateUserId();
        user.setUserId(userId);
        user.setRegisDate(new java.sql.Date(System.currentTimeMillis()));

        String sql = "INSERT INTO user (userId, fname, lname, email, password, role, regisDate, phoneNo) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);) {

            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getFname());
            pstmt.setString(3, user.getLname());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getPassword());
            pstmt.setString(6, user.getRole());
            pstmt.setDate(7, user.getRegisDate());
            pstmt.setString(8,user.getPhoneNo());

            pstmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ValidateLogin
    public boolean validateLogin(String email, String password) {
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";

        try {
            Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,email);
            pstmt.setString(2,password);

            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // GET_BY_ID
    public User gerUserById(String userId) {
        User user = new User();
        String query = "SELECT * FROM user WHERE userId Like ?";

        try {
            Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1,userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                user.setUserId(rs.getString("userId"));
                user.setFname(rs.getString("fname"));
                user.setLname(rs.getString("lname"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setRegisDate(rs.getDate("regisDate"));
                user.setPhoneNo(rs.getString("phoneNo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    // Delete
    public boolean deleteUser(String userId) {
        String sql = "DELETE FROM user WHERE userId = ?";

        try {
            Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,userId);

            int row = pstmt.executeUpdate();
            return row>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


    }

    // PUT
    public boolean updateUser(User user) {
        String sql = "UPDATE user SET userId = ?, fname = ?, lname = ?, email = ?, password = ?, role = ?, regisDate = ?, phoneNo = ?";

        try {
            Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,user.getUserId());
            pstmt.setString(2,user.getFname());
            pstmt.setString(3,user.getLname());
            pstmt.setString(4,user.getEmail());
            pstmt.setString(5,user.getPassword());
            pstmt.setString(6,user.getRole());
            pstmt.setDate(7,user.getRegisDate());
            pstmt.setString(8,user.getPhoneNo());

            int row = pstmt.executeUpdate();
            return row > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // PATCH
    public boolean updatePartialUser(String userId, User user) {
        StringBuilder sql = new StringBuilder("UPDATE user SET");
        boolean hasPrevious = false;

        if(user.getFname() != null) {
            sql.append("fname = ?");
            hasPrevious = true;
        }

        if(user.getLname() != null) {
            if(hasPrevious) sql.append(", ");
            sql.append("lname = ?");
            hasPrevious = true;
        }

        if(user.getEmail() != null) {
            if(hasPrevious) sql.append(", ");
            sql.append("email = ?");
            hasPrevious = true;
        }

        if(user.getPassword() != null) {
            if(hasPrevious) sql.append(", ");
            sql.append("password = ?");
            hasPrevious = true;
        }

        if(user.getRole() != null) {
            if(hasPrevious) sql.append(", ");
            sql.append("role = ?");
            hasPrevious = true;
        }

        if(user.getRegisDate() != null) {
            if(hasPrevious) sql.append(", ");
            sql.append("regisDate = ?");
            hasPrevious = true;
        }

        if(user.getPhoneNo() != null) {
            if(hasPrevious) sql.append(", ");
            sql.append("phoneNo = ?");
            hasPrevious = true;
        }

        sql.append(" WHERE userId = ?");

        try {
            Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            int index = 1;

            if(user.getFname() != null) {
                pstmt.setString(index++, user.getFname());
            }

            if(user.getLname() != null) {
                pstmt.setString(index++, user.getLname());
            }

            if(user.getEmail() != null) {
                pstmt.setString(index++, user.getEmail());
            }

            if(user.getPassword() != null) {
                pstmt.setString(index++, user.getPassword());
            }

            if(user.getRole() != null) {
                pstmt.setString(index++, user.getRole());
            }

            if(user.getRegisDate() != null) {
                pstmt.setDate(index++, user.getRegisDate());
            }

            if(user.getPhoneNo() != null) {
                pstmt.setString(index++, user.getPhoneNo());
            }

            pstmt.setString(index++, user.getUserId());

            int row = pstmt.executeUpdate();
            return row > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

