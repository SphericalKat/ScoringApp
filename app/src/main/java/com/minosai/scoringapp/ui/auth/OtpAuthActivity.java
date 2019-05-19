package com.minosai.scoringapp.ui.auth;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.minosai.scoringapp.R;
import com.minosai.scoringapp.base.BaseActivity;
import com.minosai.scoringapp.ui.home.MainActivity;
import com.mukesh.OtpView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OtpAuthActivity extends BaseActivity {
    @BindView(R.id.otp_pin_entry)
    OtpView otpView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_auth);
        ButterKnife.bind(this);

        @NonNull String vId = Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).getString("v_id"));

        otpView.setOtpCompletionListener(otp -> {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(vId, otp);
            FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(OtpAuthActivity.this, task -> {
                if (task.isSuccessful()) {
                    navigate(MainActivity.class);
                } else {
                    Log.e(TAG, "onComplete: ", task.getException());
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        showToast("Invalid code entered");
                        otpView.setError("Invalid code");
                    }
                }
            });
        });
    }
}
