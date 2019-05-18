package com.minosai.scoringapp.ui.home.bottomsheet;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.minosai.scoringapp.R;

public class RoundedBottomSheetDialogFragment extends BottomSheetDialogFragment {

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomSheetDialog(getContext(), getTheme());
    }

    public void showSnackbar(String message, View root) {
        Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show();
    }
}
