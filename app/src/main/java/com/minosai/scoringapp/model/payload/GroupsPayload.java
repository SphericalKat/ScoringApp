package com.minosai.scoringapp.model.payload;

import com.google.gson.annotations.SerializedName;

public class GroupsPayload {

    @SerializedName("group")
    GroupContainer container;

    public GroupsPayload() {

    }

    public GroupContainer getContainer() {
        return container;
    }

    public void setContainer(GroupContainer container) {
        this.container = container;
    }
}

