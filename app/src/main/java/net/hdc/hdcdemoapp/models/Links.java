package net.hdc.hdcdemoapp.models;

import com.google.gson.annotations.Expose;

public class Links {

    @Expose
    private String self;
    @Expose
    private String alternate;

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getAlternate() {
        return alternate;
    }

    public void setAlternate(String alternate) {
        this.alternate = alternate;
    }

}
