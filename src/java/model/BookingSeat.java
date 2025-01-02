package model;

public class BookingSeat {
    String bookingSeatId;
    String bookingId;
    String seatId;

    public BookingSeat(String bookingSeatId, String bookingId, String seatId) {
        this.bookingSeatId = bookingSeatId;
        this.bookingId = bookingId;
        this.seatId = seatId;
    }

    public String getBookingSeatId() {
        return bookingSeatId;
    }

    public void setBookingSeatId(String bookingSeatId) {
        this.bookingSeatId = bookingSeatId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    @Override
    public String toString() {
        return "BookingSeat{" +
                "bookingSeatId='" + bookingSeatId + '\'' +
                ", bookingId='" + bookingId + '\'' +
                ", seatId='" + seatId + '\'' +
                '}';
    }
}
