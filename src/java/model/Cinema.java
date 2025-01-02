package model;

import java.sql.*;
import java.time.LocalTime;


public class Cinema {
    String cinemaId;
    String name;
    Time openTime;
    Time closeTime;
    String contactNumber;
    String imageUrl;
    String address;

    public Cinema() {};

    public Cinema(String cinemaId, String name, Time openTime, Time closeTime, String contactNumber, String imageUrl, String address) {
        this.cinemaId = cinemaId;
        this.name = name;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.contactNumber = contactNumber;
        this.imageUrl = imageUrl;
        this.address = address;
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

    public Time getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Time openTime) {
        this.openTime = openTime;
    }

    public Time getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Time closeTime) {
        this.closeTime = closeTime;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "cinemaId='" + cinemaId + '\'' +
                ", name='" + name + '\'' +
                ", openTime=" + openTime +
                ", closeTime=" + closeTime +
                ", contactNumber='" + contactNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
