package com.minosai.scoringapp.base;

import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();

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

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
