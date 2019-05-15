package com.minosai.scoringapp.ui.event;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minosai.scoringapp.R;
import com.minosai.scoringapp.model.Group;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventActivity extends AppCompatActivity {

    @BindView(R.id.event_rv_groups)
    RecyclerView rvGroups;

    EventGroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        ButterKnife.bind(this);

        fetchData();
    }

    private void fetchData() {

        List<Group> groups = new ArrayList<>();
        groups.add(new Group("asfaf", "Group 1", true));
        groups.add(new Group("asdfasdf", "Group 2", true));
        groups.add(new Group("asdf", "Group 3", false));

        updateUI(groups);
    }

    private void updateUI(List<Group> groups) {

        adapter = new EventGroupAdapter(groups);

        rvGroups.setHasFixedSize(true);
        rvGroups.setLayoutManager(new LinearLayoutManager(this));
        rvGroups.setAdapter(adapter);
    }
}
