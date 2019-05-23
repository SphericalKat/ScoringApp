package com.minosai.scoringapp.model.requestbody;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.minosai.scoringapp.model.Event;
import com.minosai.scoringapp.model.LeaderboardItem;

public class GlobalLeaderboardItem {

    @SerializedName("event")
    @Expose
    private Event event;
    @SerializedName("leaderboard")
    @Expose
    private List<LeaderboardItem> leaderboard = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public GlobalLeaderboardItem() {
    }

    /**
     *
     * @param event
     * @param leaderboard
     */
    public GlobalLeaderboardItem(Event event, List<LeaderboardItem> leaderboard) {
        super();
        this.event = event;
        this.leaderboard = leaderboard;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<LeaderboardItem> getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(List<LeaderboardItem> leaderboard) {
        this.leaderboard = leaderboard;
    }

}
