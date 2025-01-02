package model;

import java.sql.Date;
import java.sql.Time;

public class Showtime {
    String showtimeId;
    Float normalSeatPrice;
    Float vipSeatPrice;
    Time showTime;
    Date showDate;
    String cinemaId;
    String name;
    String imageUrl;

    public Showtime() {}

    public Showtime(String showtimeId, Float normalSeatPrice, Float vipSeatPrice, Time showTime, Date showDate, String cinemaId, String name, String imageUrl) {
        this.showtimeId = showtimeId;
        this.normalSeatPrice = normalSeatPrice;
        this.vipSeatPrice = vipSeatPrice;
        this.showTime = showTime;
        this.showDate = showDate;
        this.cinemaId = cinemaId;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(String showtimeId) {
        this.showtimeId = showtimeId;
    }

    public Float getNormalSeatPrice() {
        return normalSeatPrice;
    }

    public void setNormalSeatPrice(Float normalSeatPrice) {
        this.normalSeatPrice = normalSeatPrice;
    }

    public Float getVipSeatPrice() {
        return vipSeatPrice;
    }

    public void setVipSeatPrice(Float vipSeatPrice) {
        this.vipSeatPrice = vipSeatPrice;
    }

    public Time getShowTime() {
        return showTime;
    }

    public void setShowTime(Time showTime) {
        this.showTime = showTime;
    }

    public Date getShowDate() {
        return showDate;
    }

    public void setShowDate(Date showDate) {
        this.showDate = showDate;
    }

    public String getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(String cinemaId) {
        this.cinemaId = cinemaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Showtime{" +
                "name='" + name + '\'' +
                ", cinemaId='" + cinemaId + '\'' +
                ", showDate=" + showDate +
                ", showTime=" + showTime +
                ", vipSeatPrice=" + vipSeatPrice +
                ", normalSeatPrice=" + normalSeatPrice +
                ", showtimeId='" + showtimeId + '\'' +
                '}';
    }
}
