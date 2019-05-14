package com.minosai.scoringapp.ui.event.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minosai.scoringapp.R;
import com.minosai.scoringapp.model.Group;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventGroupExpandedViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.event_group_expanded_text_name)
    TextView groupNameTextView;
    @BindView(R.id.event_group_expanded_button_done)
    TextView groupDoneButton;

    public EventGroupExpandedViewHolder(@NonNull View itemView) {

        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Group group) {

        groupNameTextView.setText(group.getName());
        groupDoneButton.setOnClickListener(view -> {

            //Handle click
        });
    }
}
