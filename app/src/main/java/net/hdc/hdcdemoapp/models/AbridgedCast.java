package net.hdc.hdcdemoapp.models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class AbridgedCast {

    @Expose
    private String name;
    @Expose
    private List<Object> characters = new ArrayList<Object>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Object> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Object> characters) {
        this.characters = characters;
    }

}
