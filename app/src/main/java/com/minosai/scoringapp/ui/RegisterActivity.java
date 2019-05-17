package com.minosai.scoringapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.minosai.scoringapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.navigate_login_button)
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        loginButton.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, SignInActivity.class)));
    }
}
