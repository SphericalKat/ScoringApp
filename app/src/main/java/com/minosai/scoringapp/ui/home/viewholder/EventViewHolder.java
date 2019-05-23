package com.minosai.scoringapp.ui.home.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minosai.scoringapp.R;
import com.minosai.scoringapp.model.Event;
import com.minosai.scoringapp.ui.home.callback.EventClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.home_event_item_name)
    TextView eventNameTextView;

    public EventViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Event event, EventClickListener listener) {
        eventNameTextView.setText(event.getEventName());
        eventNameTextView.setEnabled((event.getActiveGroup() != null));
        if (event.getActiveGroup() != null) {
            eventNameTextView.setOnClickListener(view -> listener.onEventClick(event));
        }
    }
}
