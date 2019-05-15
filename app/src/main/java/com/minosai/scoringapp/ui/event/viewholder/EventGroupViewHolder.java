package com.minosai.scoringapp.ui.event.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minosai.scoringapp.R;
import com.minosai.scoringapp.model.Group;
import com.minosai.scoringapp.ui.event.callback.VoteStateListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventGroupViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.event_group_text_name)
    TextView groupNameTextView;
    @BindView(R.id.event_group_button_vote)
    TextView groupVoteButton;

    public EventGroupViewHolder(@NonNull View itemView) {

        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Group group, int position, VoteStateListener listener) {

        groupNameTextView.setText(group.getName());
        groupVoteButton.setOnClickListener(view -> {
            listener.onVoteClicked(position);
        });
    }
}
