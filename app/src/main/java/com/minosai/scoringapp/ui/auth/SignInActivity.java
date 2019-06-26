package com.minosai.scoringapp.ui.auth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.minosai.scoringapp.R;
import com.minosai.scoringapp.api.ApiClient;
import com.minosai.scoringapp.api.ApiService;
import com.minosai.scoringapp.base.BaseActivity;
import com.minosai.scoringapp.model.ResponseModelPayload;
import com.minosai.scoringapp.model.payload.EmployeePayload;
import com.minosai.scoringapp.model.payload.LoginPayload;
import com.minosai.scoringapp.model.requestbody.LoginRequestModel;
import com.minosai.scoringapp.ui.home.MainActivity;
import com.minosai.scoringapp.util.Constants;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInActivity extends BaseActivity {
    @BindView(R.id.emp_id_edittext)
    EditText empIdEditText;

    @OnClick(R.id.navigate_register_button) void onClickRegister() {
        navigateAndFinish(RegisterActivity.class);
    }

    @OnClick(R.id.perform_login_button) void onClickLogin() {
        ApiService service = ApiClient.getApiService(SignInActivity.this);
        String empId = empIdEditText.getText().toString().toUpperCase();
        if (empId.isEmpty()) {
            empIdEditText.setError("This field cannot be empty");
            return;
        }
        Call<ResponseModelPayload<LoginPayload>> loginCall = service.loginEmployee(new LoginRequestModel(empId));
        loginCall.enqueue(new Callback<ResponseModelPayload<LoginPayload>>() {
            @SuppressLint("ApplySharedPref")
            @Override
            public void onResponse(@NonNull Call<ResponseModelPayload<LoginPayload>> call, @NonNull Response<ResponseModelPayload<LoginPayload>> response) {
                if (response.body() == null) {
                    showToast("Some error occured, please try again.");
                } else {
                    String token = response.body().getPayload().getToken();
                    getSharedPreferences(Constants.PREF_FILE_NAME, Context.MODE_PRIVATE).edit().putString(Constants.PREF_TOKEN, token).commit();
                    performOtpAuth(token);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModelPayload<LoginPayload>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    private void performOtpAuth(String token) {
//        Log.d(TAG, "performOtpAuth: " + getSharedPreferences(Constants.PREF_FILE_NAME, Context.MODE_PRIVATE).getString(Constants.PREF_TOKEN, ""));
        ApiService service = ApiClient.getApiService(SignInActivity.this, token);


        Call<ResponseModelPayload<EmployeePayload>> fetchEmployeeCall = service.fetchEmployeeDetails();
        fetchEmployeeCall.enqueue(new Callback<ResponseModelPayload<EmployeePayload>>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModelPayload<EmployeePayload>> call, @NonNull Response<ResponseModelPayload<EmployeePayload>> response) {
                if (response.body() == null) {
                    showToast("Some error occurred, try again later.");
                    Log.d(TAG, "onResponse: " + call.request().headers());
                } else {
                    String phoneNo = response.body().getPayload().getEmployee().getPhoneNumber();
                    if (!phoneNo.startsWith("+")) {
                        phoneNo = "+91" + phoneNo;
                    }
                    Log.d(TAG, "onResponse: " + phoneNo);
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNo, 60, TimeUnit.SECONDS, SignInActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                            Log.d(TAG, "onVerificationCompleted: " + "Auto-verified");
                            FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(SignInActivity.this, task -> {
                                if (task.isSuccessful()) {
                                    navigateAndFinish(MainActivity.class);
                                } else {
                                    Log.e(TAG, "onComplete: ", task.getException());
                                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                        showToast("Invalid code entered");
                                    }
                                }
                            });
                            navigateAndFinish(MainActivity.class);
                        }

                        @Override
                        public void onVerificationFailed(FirebaseException e) {
                            Log.e(TAG, "onVerificationFailed", e);

                            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                showToast("Invalid request.");
                            } else if (e instanceof FirebaseTooManyRequestsException) {
                                showToast("SMS quota exceeded!");
                            }
                        }

                        @Override
                        public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                            Intent intent = new Intent(SignInActivity.this, OtpAuthActivity.class);
                            intent.putExtra("v_id", verificationId);
                            navigateAndFinish(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModelPayload<EmployeePayload>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
    }
}
