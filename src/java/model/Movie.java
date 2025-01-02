/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.*;
import java.util.Base64;

/**
 *
 * @author LENOVO
 */
public class Movie {
    private String movieId;
    private String title;
    private Date releaseDate;
    private Integer duration;
    private String synopsis;
    private String trailer;
    private String imageUrl;

    public Movie(String movieId, String title, Date releaseDate, Integer duration, String synopsis, String trailer, String imageUrl) {
        this.movieId = movieId;
        this.title = title;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.synopsis = synopsis;
        this.trailer = trailer;
        this.imageUrl = imageUrl;
    }

    public Movie() {}

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

   /* public String convertToBase64() {
        return Base64.getEncoder().encodeToString(image);
    } */

    @Override
    public String toString() {
        return "Movie{" +
                "movieId='" + movieId + '\'' +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", duration=" + duration +
                '}';
    }
}
