package com.minosai.scoringapp.model.payload;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventsPayload {

    List<Event> events;

    public EventsPayload() {

    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}

class Event {

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
