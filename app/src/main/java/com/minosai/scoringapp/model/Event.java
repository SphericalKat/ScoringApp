package com.minosai.scoringapp.model;

import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("_id")
    String id;
    boolean active;
    @SerializedName("event_name")
    String eventName;

    public Event() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
