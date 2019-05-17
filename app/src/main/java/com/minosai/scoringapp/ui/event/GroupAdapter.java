package com.minosai.scoringapp.ui.event;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minosai.scoringapp.R;
import com.minosai.scoringapp.model.Group;
import com.minosai.scoringapp.ui.event.callback.VoteListener;
import com.minosai.scoringapp.ui.event.callback.VoteStateListener;
import com.minosai.scoringapp.ui.event.viewholder.GroupDisabledViewHolder;
import com.minosai.scoringapp.ui.event.viewholder.GroupExpandedViewHolder;
import com.minosai.scoringapp.ui.event.viewholder.GroupViewHolder;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements VoteStateListener {

    List<Group> groups;
    VoteListener listener;
    int expandPosition = -1;

    public GroupAdapter(List<Group> groups, VoteListener listener) {
        this.groups = groups;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (groups.get(position).isPerformed()) {
            if (expandPosition == position) {
                return 2;
            }
            return 1;
        }
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_group_disabled, parent, false);
                return new GroupDisabledViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_group, parent, false);
                return new GroupViewHolder(view);
            case 2:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_group_expanded, parent, false);
                return new GroupExpandedViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Group group = groups.get(position);

        if (group.isPerformed()) {
            if (expandPosition == position) {
                ((GroupExpandedViewHolder) holder).bind(group, position, this);
            } else {
                ((GroupViewHolder) holder).bind(group, position, this);
            }
        } else {
            ((GroupDisabledViewHolder) holder).bind(group, position);
        }
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    @Override
    public void onVoteClicked(int position) {

        expandPosition = position;
        notifyDataSetChanged();
    }

    @Override
    public void onDoneClicked(int position, int score) {

        listener.vote(groups.get(position), score);
        expandPosition = -1;
        notifyDataSetChanged();
    }
}