package com.minosai.scoringapp.ui.home.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.minosai.scoringapp.R;
import com.shawnlin.numberpicker.NumberPicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VotingBottomSheetFragnent extends RoundedBottomSheetDialogFragment {

    @BindView(R.id.vote_number_picker)
    NumberPicker voteNumberPicker;

    String eventId;
    String groupName;

    public static VotingBottomSheetFragnent newInstance(String eventId) {
        VotingBottomSheetFragnent instance = new VotingBottomSheetFragnent();
        instance.initData(eventId);
        return instance;
    }

    private void initData(String eventId) {
        this.eventId = eventId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_voting_bottomsheet, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.voting_button_vote)
    void voteButtonOnClick() {
        int vote = voteNumberPicker.getValue();
        Toast.makeText(getContext(), "" + vote, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.voting_group_close)
    void closeButtonOnClick() {
        this.dismiss();
    }
}
