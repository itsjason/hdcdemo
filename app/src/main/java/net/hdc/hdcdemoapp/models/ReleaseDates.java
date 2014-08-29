package net.hdc.hdcdemoapp.models;

import com.google.gson.annotations.Expose;

public class ReleaseDates {

    @Expose
    private String theater;

    public String getTheater() {
        return theater;
    }

    public void setTheater(String theater) {
        this.theater = theater;
    }

}
