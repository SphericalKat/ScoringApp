package com.minosai.scoringapp.base;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    protected void navigate(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    protected void navigate(Intent intent) {
        startActivity(intent);
    }

    protected void navigateAndFinish(Class<?> cls) {
        navigate(cls);
        finish();
    }

    protected void navigateAndFinish(Intent intent) {
        navigate(intent);
        finish();
    }
}
