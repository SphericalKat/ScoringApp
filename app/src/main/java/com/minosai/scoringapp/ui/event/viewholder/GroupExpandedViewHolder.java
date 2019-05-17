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

public class GroupExpandedViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.event_group_expanded_text_name)
    TextView groupNameTextView;
    @BindView(R.id.event_group_expanded_button_done)
    TextView groupDoneButton;

    public GroupExpandedViewHolder(@NonNull View itemView) {

        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Group group, int position, VoteStateListener listener) {

        groupNameTextView.setText(group.getName());
        groupDoneButton.setOnClickListener(view -> {
            listener.onDoneClicked(position, 1); //TODO: Fetch score from UI
        });
    }
}
