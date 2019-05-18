package com.minosai.scoringapp.ui.home.bottomsheet;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.minosai.scoringapp.R;
import com.minosai.scoringapp.api.ApiClient;
import com.minosai.scoringapp.api.ApiService;
import com.minosai.scoringapp.model.Group;
import com.minosai.scoringapp.model.Meta;
import com.minosai.scoringapp.model.ResponseModel;
import com.minosai.scoringapp.model.ResponseModelPayload;
import com.minosai.scoringapp.model.payload.GroupsPayload;
import com.minosai.scoringapp.model.requestbody.VoteRequestModel;
import com.shawnlin.numberpicker.NumberPicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VotingBottomSheetFragnent extends RoundedBottomSheetDialogFragment {

    String TAG = this.getClass().getSimpleName();

    @BindView(R.id.vote_number_picker)
    NumberPicker voteNumberPicker;
    @BindView(R.id.voting_layout_container)
    LinearLayout root;
    @BindView(R.id.voting_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.voting_group_name)
    TextView groupNameTextView;
    @BindView(R.id.voting_layout_main)
    ConstraintLayout mainLayout;

    ApiService apiService;

    String eventId;
    Group group;

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

        apiService = ApiClient.getApiService(getContext());
        fetchData();

        return view;
    }

    private void fetchData() {
        showLoading();
        apiService.fetchGroupsList(eventId).enqueue(new Callback<ResponseModelPayload<GroupsPayload>>() {
            @Override
            public void onResponse(Call<ResponseModelPayload<GroupsPayload>> call,
                                   Response<ResponseModelPayload<GroupsPayload>> response) {
                hideLoading();
                boolean toContinue = handleResponse(response.isSuccessful(), response.body().getMeta());
                if (!toContinue) {
                    return;
                }

                Group activeGroup = response.body().getPayload().getContainer().getActiveGroup();
                if (activeGroup == null) {
                    Toast.makeText(getContext(), getContext().getString(R.string.response_error),
                            Toast.LENGTH_SHORT).show();
                    dismiss();
                    return;
                }
                updateUI(activeGroup);
            }

            @Override
            public void onFailure(Call<ResponseModelPayload<GroupsPayload>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), getContext().getString(R.string.network_error),
                        Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
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

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.GONE);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
        mainLayout.setVisibility(View.VISIBLE);
    }

    private void updateUI(Group activeGroup) {
        group = activeGroup;
        groupNameTextView.setText(activeGroup.getName());
    }

    @OnClick(R.id.voting_button_vote)
    void voteButtonOnClick() {

        int vote = voteNumberPicker.getValue();
        VoteRequestModel model = new VoteRequestModel(eventId, vote, group.getName());

        showLoading();
        apiService.putGroupVote(model).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                hideLoading();

                if (response.body() == null) {
                    showSnackbar("Submission already exists", root);
                    return;
                }
                if (handleResponse(response.isSuccessful(), response.body().getMeta())) {
                    showSnackbar("Successfully saved vote", root);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                hideLoading();
                showSnackbar(getContext().getString(R.string.network_error), root);
            }
        });
    }

    @OnClick(R.id.voting_group_close)
    void closeButtonOnClick() {
        this.dismiss();
    }
}
