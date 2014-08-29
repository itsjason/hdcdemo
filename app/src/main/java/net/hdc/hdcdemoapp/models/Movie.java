package net.hdc.hdcdemoapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    @Expose
    private String title;
    @Expose
    private Integer year;
    @Expose
    private String runtime;
    @SerializedName("release_dates")
    @Expose
    private ReleaseDates releaseDates;
    @Expose
    private Ratings ratings;
    @Expose
    private String synopsis;
    @Expose
    private Posters posters;
    @SerializedName("abridged_cast")
    @Expose
    private List<AbridgedCast> abridgedCast = new ArrayList<AbridgedCast>();
    @Expose
    private Links links;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public ReleaseDates getReleaseDates() {
        return releaseDates;
    }

    public void setReleaseDates(ReleaseDates releaseDates) {
        this.releaseDates = releaseDates;
    }

    public Ratings getRatings() {
        return ratings;
    }

    public void setRatings(Ratings ratings) {
        this.ratings = ratings;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Posters getPosters() {
        return posters;
    }

    public void setPosters(Posters posters) {
        this.posters = posters;
    }

    public List<AbridgedCast> getAbridgedCast() {
        return abridgedCast;
    }

    public void setAbridgedCast(List<AbridgedCast> abridgedCast) {
        this.abridgedCast = abridgedCast;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

}
