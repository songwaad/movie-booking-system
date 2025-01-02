package model;

public class Seat {
    String showtimeId;
    String seatId;
    String row;
    Integer col;
    String seatType;
    String status;

    public Seat() {}

    public Seat(String showtimeId, String seatId, String row, Integer col, String seatType, String status) {
        this.showtimeId = showtimeId;
        this.seatId = seatId;
        this.row = row;
        this.col = col;
        this.seatType = seatType;
        this.status = status;
    }

    public String getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(String showtimeId) {
        this.showtimeId = showtimeId;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
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

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "showtimeId='" + showtimeId + '\'' +
                ", seatId='" + seatId + '\'' +
                ", row='" + row + '\'' +
                ", col=" + col +
                ", seatType='" + seatType + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
