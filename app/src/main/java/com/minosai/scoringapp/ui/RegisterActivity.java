package com.minosai.scoringapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.minosai.scoringapp.R;
import com.minosai.scoringapp.api.ApiClient;
import com.minosai.scoringapp.api.ApiService;
import com.minosai.scoringapp.base.BaseActivity;
import com.minosai.scoringapp.model.ResponseModelPayload;
import com.minosai.scoringapp.model.payload.EmployeePayload;
import com.minosai.scoringapp.model.requestbody.RegisterRequestModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.navigate_login_button)
    Button loginButton;

    @BindView(R.id.perform_register_button)
    Button registerButton;

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
        loginButton.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, SignInActivity.class)));
        registerButton.setOnClickListener(v -> {
            if (!validateFields()) {
                return;
            }
            String empId = empIdEditText.getText().toString();
            String phoneNo = phoneEditText.getText().toString();
            if (phoneNo.startsWith("+")) {
                phoneNo = phoneNo.substring(3);
            }
            Call<ResponseModelPayload<EmployeePayload>> registerCall = apiService.registerEmployee(new RegisterRequestModel(empId, phoneNo));
            registerCall.enqueue(new Callback<ResponseModelPayload<EmployeePayload>>() {
                @Override
                public void onResponse(@NonNull Call<ResponseModelPayload<EmployeePayload>> call, @NonNull Response<ResponseModelPayload<EmployeePayload>> response) {
                    if (response.body() != null && !response.body().getMeta().isStatusSuccess()) {
                        showToast(response.body().getMeta().getMessage());
                        return;
                    }
                    if (response.body() == null) {
                        Log.e(TAG, "onResponse: " + response.errorBody());
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
