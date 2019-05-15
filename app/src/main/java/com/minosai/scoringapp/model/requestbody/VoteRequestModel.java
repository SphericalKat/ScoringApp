package com.minosai.scoringapp.model.requestbody;

import com.google.gson.annotations.SerializedName;

public class VoteRequestModel {

    @SerializedName("event_id")
    String eventId;
    int score;
    @SerializedName("group_name")
    String groupName;

    public VoteRequestModel() {

    }

    public VoteRequestModel(String eventId, int score, String groupName) {
        this.eventId = eventId;
        this.score = score;
        this.groupName = groupName;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
