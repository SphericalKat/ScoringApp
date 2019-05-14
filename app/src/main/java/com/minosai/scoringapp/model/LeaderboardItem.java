package com.minosai.scoringapp.model;

import com.google.gson.annotations.SerializedName;

public class LeaderboardItem {

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
