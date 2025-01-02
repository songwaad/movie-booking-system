package modelDAO;

import model.Payment;
import utility.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    private DatabaseConnector dbConnector;

    public PaymentDAO(DatabaseConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    // generatePaymentId
    private String generatePaymentId() {
        int paymentCount = getPaymentCount();
        return String.format("pay%010d", paymentCount + 1);
    }

    // getPaymentCount
    private int getPaymentCount() {
        String sql = "SELECT COUNT(*) FROM payment";
        try (Connection conn = dbConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if(rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // GET_ALL
    public List<Payment> getAllPayment() {
        List<Payment> payments = new ArrayList<>();
        try (Connection conn = dbConnector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM payment")) {

            while (rs.next()) {
                String paymentId = rs.getString("paymentId");
                String bookingId = rs.getString("bookingId");
                Float amount = rs.getFloat("amount");
                String paymentMethod = rs.getString("paymentMethod");
                Date paymentDate = rs.getDate("paymentDate");
                String status = rs.getString("status");

                payments.add(new Payment(paymentId,bookingId,amount,paymentMethod,paymentDate,status));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return payments;
    }

    // ADD
    public boolean addPayment(Payment payment) {
        String paymentId = generatePaymentId();
        System.out.println("paymentId : " + paymentId);
        Date paymentDate = new java.sql.Date(System.currentTimeMillis());

        String sql = "INSERT INTO payment (paymentId, bookingId, amount, paymentMethod, paymentDate, status) VALUES(?,?,?,?,?,?)";
        try {
            Connection conn = dbConnector.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,paymentId);
            pstmt.setString(2, payment.getBookingId());
            pstmt.setFloat(3,payment.getAmount());
            pstmt.setString(4, payment.getPaymentMethod());
            pstmt.setDate(5,paymentDate);
            pstmt.setString(6,payment.getStatus());

            pstmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update

    public  boolean updateStatus(String paymentId, String bookingId, String status) {
        String updatePaymentSql = "UPDATE payment SET status = ? WHERE paymentId = ?";
        String updateBookingSql = "UPDATE booking SET status = ? WHERE bookingId = ?";

        try (Connection conn = dbConnector.getConnection();
             PreparedStatement paymentStmt = conn.prepareStatement(updatePaymentSql);
             PreparedStatement bookingStmt = conn.prepareStatement(updateBookingSql)) {

            // อัปเดต status ใน payment table
            paymentStmt.setString(1, status);
            paymentStmt.setString(2, paymentId);
            paymentStmt.executeUpdate();

            // อัปเดต status ใน booking table
            bookingStmt.setString(1, status);
            bookingStmt.setString(2, bookingId);
            bookingStmt.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
