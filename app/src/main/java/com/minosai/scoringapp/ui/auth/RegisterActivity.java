package com.minosai.scoringapp.ui.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.minosai.scoringapp.R;
import com.minosai.scoringapp.api.ApiClient;
import com.minosai.scoringapp.api.ApiService;
import com.minosai.scoringapp.base.BaseActivity;
import com.minosai.scoringapp.model.Meta;
import com.minosai.scoringapp.model.ResponseModelPayload;
import com.minosai.scoringapp.model.payload.EmployeePayload;
import com.minosai.scoringapp.model.requestbody.RegisterRequestModel;
import com.minosai.scoringapp.ui.home.MainActivity;
import com.minosai.scoringapp.util.Constants;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.emp_id_registration)
    EditText empIdEditText;

    @BindView(R.id.phone_no_register)
    EditText phoneEditText;

    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
        apiService = ApiClient.getApiService(this);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            navigate(MainActivity.class);
            finish();
        }
    }

    @OnClick(R.id.navigate_login_button)
    void loginOnClick() {
        navigateAndFinish(SignInActivity.class);
    }

    @OnClick(R.id.perform_register_button)
    void registerOnClick() {
        if (!validateFields()) {
            return;
        }
        String empId = empIdEditText.getText().toString();
        String phoneNo = phoneEditText.getText().toString();
        if (phoneNo.startsWith("+")) {
            phoneNo = phoneNo.substring(3);
        }
        register(empId, phoneNo);
    }

    private void register(String empId, String phoneNo) {
        Call<ResponseModelPayload<EmployeePayload>> registerCall = apiService.registerEmployee(new RegisterRequestModel(empId, phoneNo));
        registerCall.enqueue(new Callback<ResponseModelPayload<EmployeePayload>>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModelPayload<EmployeePayload>> call, @NonNull Response<ResponseModelPayload<EmployeePayload>> response) {
                if (response.body() != null && !response.body().getMeta().isStatusSuccess()) {
                    showToast(response.body().getMeta().getMessage());
                    return;
                }
                if (response.body() == null) {
                    if (response.code() == 400) {
                        showToast("This employee ID is already registered");
                    } else if (response.code() == 500) {
                        showToast("An error occurred, please try again later.");
                    }
                    return;
                }
                showToast("Registration successful");
                navigateAndFinish(SignInActivity.class);
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModelPayload<EmployeePayload>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    private boolean validateFields() {
        if (empIdEditText.getText().toString().isEmpty()) {
            empIdEditText.setError("Field cannot be empty!");
            return false;
        } else if (phoneEditText.getText().toString().isEmpty()) {
            phoneEditText.setError("Field cannot be empty!");
            return false;
        }
        return true;
    }
}