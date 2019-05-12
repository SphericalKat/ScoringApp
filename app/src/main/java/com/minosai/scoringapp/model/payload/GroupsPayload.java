package com.minosai.scoringapp.model.payload;

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

class Group {

    String name;

    public Group() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
