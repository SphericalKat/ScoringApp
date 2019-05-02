package com.minosai.scoringapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

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
