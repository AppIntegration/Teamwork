package com.omertron.imdbapi.Integation;

import java.util.List;

/**
 * Created by SK on 2017/6/9.
 */
public class MongoModel {

    public MongoModel(int year, String type, String title, String tagline, float rating, String imdbId,
                      List<String> genres, double numVotes, List<String> casts,
                      List<String> directors, List<String> writers) {
        this.year = year;
        this.type = type;
        this.title = title;
        this.tagline = tagline;
        this.rating = rating;
        this.imdbId = imdbId;
        this.genres = genres;
        this.numVotes = numVotes;

        this.casts = casts;
        this.directors = directors;
        this.writers = writers;
    }

    private int year;
    private String type;
    private String title;
    private String tagline;
    private float rating;
    private String imdbId;
    private List<String> genres;
    private double numVotes;

    private List<String> casts;
    private List<String>  directors;
    private List<String> writers;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public double getNumVotes() {
        return numVotes;
    }

    public void setNumVotes(double numVotes) {
        this.numVotes = numVotes;
    }



    public List<String> getCasts() {
        return casts;
    }

    public void setCasts(List<String> casts) {
        this.casts = casts;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<String> getWriters() {
        return writers;
    }

    public void setWriters(List<String> writers) {
        this.writers = writers;
    }
}
