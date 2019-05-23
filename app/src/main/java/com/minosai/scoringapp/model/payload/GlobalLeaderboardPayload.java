package com.minosai.scoringapp.model.payload;

import com.google.gson.annotations.SerializedName;
import com.minosai.scoringapp.model.requestbody.GlobalLeaderboardItem;

import java.util.List;

public class GlobalLeaderboardPayload {
    @SerializedName("global_leaderboard")
    List<GlobalLeaderboardItem> globalLeaderboardItems;

    public GlobalLeaderboardPayload(List<GlobalLeaderboardItem> globalLeaderboardItems) {
        this.globalLeaderboardItems = globalLeaderboardItems;
    }

    public GlobalLeaderboardPayload() {
    }

    public List<GlobalLeaderboardItem> getGlobalLeaderboardItems() {
        return globalLeaderboardItems;
    }

    public void setGlobalLeaderboardItems(List<GlobalLeaderboardItem> globalLeaderboardItems) {
        this.globalLeaderboardItems = globalLeaderboardItems;
    }
}
