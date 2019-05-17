package com.minosai.scoringapp.ui.event.callback;

public interface VoteStateListener {

    void onVoteClicked(int position);

    void onDoneClicked(int position, int score);
}
