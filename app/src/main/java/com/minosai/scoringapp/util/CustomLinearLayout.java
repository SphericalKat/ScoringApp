package com.minosai.scoringapp.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

@CoordinatorLayout.DefaultBehavior(MoveUpwardBehavior.class)
public class CustomLinearLayout extends LinearLayout {
    public CustomLinearLayout(Context context) {
        super(context);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
