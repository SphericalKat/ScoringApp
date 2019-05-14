package com.minosai.scoringapp.model.payload;

import com.google.gson.annotations.SerializedName;
import com.minosai.scoringapp.model.LeaderboardItem;

import java.util.List;

public class LeaderboardPayload {

    @SerializedName("leaderboard")
    List<LeaderboardItem> leaderboardItems;

    public LeaderboardPayload() {

    }

    public List<LeaderboardItem> getLeaderboardItems() {
        return leaderboardItems;
    }

    public void setLeaderboardItems(List<LeaderboardItem> leaderboardItems) {
        this.leaderboardItems = leaderboardItems;
    }
}

