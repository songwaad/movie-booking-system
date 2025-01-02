package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Base64;

public class User {
    String userId;
    String fname;
    String lname;
    String email;
    String password;
    String role;
    Date regisDate;
    String phoneNo;


    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public User() {}

    public User(String userId, String fname, String lname, String email, String password, String role, Date regisDate, String phoneNo) {
        this.userId = userId;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = hashPassword(password);
        this.role = role;
        this.regisDate = regisDate;
        this.phoneNo = phoneNo;
    }

    public User(String fname, String lname, String email, String password, String role, String phoneNo) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = hashPassword(password);
        this.role = role;
        this.phoneNo = phoneNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getRegisDate() {
        return regisDate;
    }

    public void setRegisDate(Date regisDate) {
        this.regisDate = regisDate;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", regisDate=" + regisDate +
                ", phoneNo='" + phoneNo + '\'' +
                '}';
    }
}
