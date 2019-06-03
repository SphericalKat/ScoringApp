package com.minosai.scoringapp.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.minosai.scoringapp.R;
import com.minosai.scoringapp.base.BaseActivity;

import me.jfenn.attribouter.Attribouter;

public class AttribouterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attribouter);
        Fragment fragment = Attribouter.from(this).toFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.attribouter_container, fragment).commit();
    }
}
