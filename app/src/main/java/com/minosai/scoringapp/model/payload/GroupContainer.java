package com.minosai.scoringapp.model.payload;

import com.google.gson.annotations.SerializedName;
import com.minosai.scoringapp.model.Group;

public class GroupContainer {

    @SerializedName("active_group")
    Group activeGroup;

    public GroupContainer() {

    }

    public Group getActiveGroup() {
        return activeGroup;
    }

    public void setActiveGroup(Group activeGroup) {
        this.activeGroup = activeGroup;
    }
}
