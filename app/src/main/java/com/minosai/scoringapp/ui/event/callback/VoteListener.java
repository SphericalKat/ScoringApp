package com.minosai.scoringapp.ui.event.callback;

import com.minosai.scoringapp.model.Group;

public interface VoteListener {

    void vote(Group group, int score);

}
