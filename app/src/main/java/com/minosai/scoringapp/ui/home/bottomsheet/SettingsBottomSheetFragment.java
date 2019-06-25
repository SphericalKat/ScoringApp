package com.minosai.scoringapp.ui.home.bottomsheet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.minosai.scoringapp.R;
import com.minosai.scoringapp.api.ApiClient;
import com.minosai.scoringapp.api.ApiService;
import com.minosai.scoringapp.model.Meta;
import com.minosai.scoringapp.model.ResponseModel;
import com.minosai.scoringapp.model.requestbody.EmpIdRequestModel;
import com.minosai.scoringapp.ui.auth.RegisterActivity;
import com.minosai.scoringapp.util.Constants;
import com.minosai.scoringapp.util.CustomLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsBottomSheetFragment extends RoundedBottomSheetDialogFragment {

    @BindView(R.id.settings_input_emp_id)
    EditText empIdEditText;
    @BindView(R.id.settings_layout_container)
    CustomLinearLayout root;
    @BindView(R.id.settings_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.settings_layout_main)
    ConstraintLayout mainLayout;

    private ApiService apiService;

    public static SettingsBottomSheetFragment getInstance() {
        SettingsBottomSheetFragment instance = new SettingsBottomSheetFragment();
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_bottomsheet, container, false);
        ButterKnife.bind(this, view);
        apiService = ApiClient.getApiService(getContext());
        return view;
    }

    @OnClick(R.id.settings_button_close)
    void closeOnClick() {
        this.dismiss();
    }

    @OnClick(R.id.settings_button_logout)
    void logoutOnClick() {
        Activity activity = requireActivity();
        activity.getSharedPreferences(Constants.PREF_FILE_NAME, Context.MODE_PRIVATE).edit().clear().apply();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(activity, RegisterActivity.class));
        activity.finish();
    }

    @OnClick(R.id.settings_button_update)
    void updateOnClick() {

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(empIdEditText.getWindowToken(), 0);

        String newId = empIdEditText.getText().toString();
        if (newId.isEmpty()) {
            showSnackbar("Employee ID cannot be empty", root);
            return;
        }
        updateId(newId);
    }

    private void updateId(String newId) {
        showLoading();
        apiService.updateEmployeeId(new EmpIdRequestModel(newId)).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                hideLoading();
                if (response.body() == null) {
                    showSnackbar(getContext().getString(R.string.response_error), root);
                }
                if (handleResponse(response.isSuccessful(), response.body().getMeta())) {
                    showSnackbar("Successfully updated employee ID", root);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                hideLoading();
                showSnackbar(getContext().getString(R.string.network_error), root);
            }
        });
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.GONE);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
        mainLayout.setVisibility(View.VISIBLE);
    }

    private boolean handleResponse(boolean successful, Meta meta) {

        if (!successful) {
            Toast.makeText(getContext(), getContext()
                    .getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            dismiss();
            return false;
        }

        if (!meta.isStatusSuccess()) {
            Toast.makeText(getContext(),
                    meta.getMessage(), Toast.LENGTH_SHORT).show();
            dismiss();
            return false;
        }

        return true;
    }
}
