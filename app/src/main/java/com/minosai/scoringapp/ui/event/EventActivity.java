package com.minosai.scoringapp.ui.event;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.minosai.scoringapp.R;
import com.minosai.scoringapp.model.payload.GroupsPayload;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
    }
}
