package com.example.nipunac.popularmovies_v1.model;



public class Movie  {

    private String id;
    private String posterURL;
    private String originalTitle;
    private String moviePosterThumbnail;
    private String plotSynopsis;
    private String userRating;
    private String releaseDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getMoviePosterThumbnail() {
        return moviePosterThumbnail;
    }

    public void setMoviePosterThumbnail(String moviePosterThumbnail) {
        this.moviePosterThumbnail = moviePosterThumbnail;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
