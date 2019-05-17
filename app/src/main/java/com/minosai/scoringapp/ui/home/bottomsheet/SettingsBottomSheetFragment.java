package com.minosai.scoringapp.ui.home.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.minosai.scoringapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsBottomSheetFragment extends RoundedBottomSheetDialogFragment {

    @BindView(R.id.settings_input_emp_id)
    EditText empIdEditText;

    public static SettingsBottomSheetFragment getInstance() {
        SettingsBottomSheetFragment instance = new SettingsBottomSheetFragment();
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_bottomsheet, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.settings_button_close)
    void closeOnClick() {
        this.dismiss();
    }

    @OnClick(R.id.settings_button_logout)
    void logoutOnClick() {

    }
}
