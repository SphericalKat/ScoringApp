package com.minosai.scoringapp.model.payload;

import com.minosai.scoringapp.model.Group;

import java.util.List;

public class GroupsPayload {

    List<Group> groups;

    public GroupsPayload() {

    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}

