package com.example.springbootdemo.standalone;

public class VideoGame {
    private String title;
    private String platform;
    private int releaseYear;
    private double rating;

    public VideoGame(String title, String platform, int releaseYear, double rating) {
        this.title = title;
        this.platform = platform;
        this.releaseYear = releaseYear;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getPlatform() {
        return platform;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public double getRating() {
        return rating;
    }
}
