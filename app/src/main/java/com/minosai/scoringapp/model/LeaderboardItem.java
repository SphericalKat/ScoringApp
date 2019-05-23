package com.minosai.scoringapp.model;

import com.google.gson.annotations.SerializedName;

public class LeaderboardItem implements Comparable<LeaderboardItem>{

    @SerializedName("group_name")
    String groupName;
    String score;

    public LeaderboardItem() {

    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public int compareTo(LeaderboardItem o) {
        return (int) (Float.parseFloat(o.getScore()) - Float.parseFloat(this.getScore()));
    }
}
