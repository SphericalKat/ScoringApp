package com.minosai.scoringapp.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minosai.scoringapp.R;
import com.minosai.scoringapp.model.Event;
import com.minosai.scoringapp.ui.home.callback.EventClickListener;
import com.minosai.scoringapp.ui.home.viewholder.EventViewHolder;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventViewHolder> {

    private List<Event> events;
    private EventClickListener listener;

    EventAdapter(List<Event> events, EventClickListener listener) {
        this.events = events;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.bind(events.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
