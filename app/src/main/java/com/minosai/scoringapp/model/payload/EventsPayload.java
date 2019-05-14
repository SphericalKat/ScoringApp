package com.minosai.scoringapp.model.payload;

import com.minosai.scoringapp.model.Event;

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

