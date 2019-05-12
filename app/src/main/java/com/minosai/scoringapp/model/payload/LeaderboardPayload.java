package com.minosai.scoringapp.model.payload;

import com.google.gson.annotations.SerializedName;

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

class LeaderboardItem {

    @SerializedName("group_name")
    String groupName;
    int score;

    public LeaderboardItem() {

    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
