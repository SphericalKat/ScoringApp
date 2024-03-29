package com.minosai.scoringapp.ui.event.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minosai.scoringapp.R;
import com.minosai.scoringapp.model.Group;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupDisabledViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.evnet_group_disabled_text_name)
    TextView groupNameTextView;

    public GroupDisabledViewHolder(@NonNull View itemView) {

        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Group group, int position) {

        groupNameTextView.setText(group.getName());
    }
}
