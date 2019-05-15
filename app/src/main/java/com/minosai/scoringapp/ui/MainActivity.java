package com.minosai.scoringapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.minosai.scoringapp.R;
import com.minosai.scoringapp.base.BaseActivity;
import com.minosai.scoringapp.ui.event.EventActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_button)
    Button buttonMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        buttonMain.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, EventActivity.class);
            startActivity(intent);
        });
    }
}
