package com.minosai.scoringapp.model;

import com.google.gson.annotations.SerializedName;

public class Group {

    @SerializedName("_id")
    String id;
    String name;
    boolean performed;

    public Group() {

    }

    public Group(String id, String name, boolean performed) {
        this.id = id;
        this.name = name;
        this.performed = performed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPerformed() {
        return performed;
    }

    public void setPerformed(boolean performed) {
        this.performed = performed;
    }
}
