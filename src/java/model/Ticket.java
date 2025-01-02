package model;

import java.sql.Date;
import java.sql.Time;

public class Ticket {
    String userId;
    String bookingId;
    String bookingSeatId;
    String seatId;
    Date showDate;
    Time showTime;
    String row;
    Integer col;
    String status;
    String movieTitle;
    String cinemaName;


    public Ticket() {};

    public Ticket(String userId, String bookingId, String bookingSeatId, String seatId, Date showDate, Time showTime, String row, Integer col, String status, String movieTitle, String cinemaName) {
        this.userId = userId;
        this.bookingId = bookingId;
        this.bookingSeatId = bookingSeatId;
        this.seatId = seatId;
        this.showDate = showDate;
        this.showTime = showTime;
        this.row = row;
        this.col = col;
        this.status = status;
        this.movieTitle = movieTitle;
        this.cinemaName = cinemaName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingSeatId() {
        return bookingSeatId;
    }

    public void setBookingSeatId(String bookingSeatId) {
        this.bookingSeatId = bookingSeatId;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public Date getShowDate() {
        return showDate;
    }

    public void setShowDate(Date showDate) {
        this.showDate = showDate;
    }

    public Time getShowTime() {
        return showTime;
    }

    public void setShowTime(Time showTime) {
        this.showTime = showTime;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "userId='" + userId + '\'' +
                ", bookingId='" + bookingId + '\'' +
                ", bookingSeatId='" + bookingSeatId + '\'' +
                ", seatId='" + seatId + '\'' +
                ", showDate=" + showDate +
                ", showTime=" + showTime +
                ", row='" + row + '\'' +
                ", col=" + col +
                ", status='" + status + '\'' +
                ", movieTitle='" + movieTitle + '\'' +
                ", cinemaName='" + cinemaName + '\'' +
                '}';
    }
}
