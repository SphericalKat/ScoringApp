package com.minosai.scoringapp.ui.event;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minosai.scoringapp.R;
import com.minosai.scoringapp.model.Group;
import com.minosai.scoringapp.ui.event.viewholder.EventGroupDisabledViewHolder;
import com.minosai.scoringapp.ui.event.viewholder.EventGroupExpandedViewHolder;
import com.minosai.scoringapp.ui.event.viewholder.EventGroupViewHolder;

import java.util.List;

public class EventGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Group> groups;
    int expandPosition = -1;

    public EventGroupAdapter(List<Group> groups) {
        this.groups = groups;
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
                return new EventGroupDisabledViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_group, parent, false);
                return new EventGroupViewHolder(view);
            case 2:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_group_expanded, parent, false);
                return new EventGroupExpandedViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Group group = groups.get(position);

        if (group.isPerformed()) {
            if (expandPosition == position) {
                ((EventGroupExpandedViewHolder) holder).bind(group);
            } else {
                ((EventGroupViewHolder) holder).bind(group);
            }
        } else {
            ((EventGroupDisabledViewHolder) holder).bind(group);
        }
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }
}
