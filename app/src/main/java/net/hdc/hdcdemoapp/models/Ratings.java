package net.hdc.hdcdemoapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ratings {

    @SerializedName("critics_score")
    @Expose
    private Integer criticsScore;
    @SerializedName("audience_score")
    @Expose
    private Integer audienceScore;

    public Integer getCriticsScore() {
        return criticsScore;
    }

    public void setCriticsScore(Integer criticsScore) {
        this.criticsScore = criticsScore;
    }

    public Integer getAudienceScore() {
        return audienceScore;
    }

    public void setAudienceScore(Integer audienceScore) {
        this.audienceScore = audienceScore;
    }

}
