package com.minosai.scoringapp.ui.event;

import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minosai.scoringapp.R;
import com.minosai.scoringapp.api.ApiClient;
import com.minosai.scoringapp.api.ApiService;
import com.minosai.scoringapp.base.BaseActivity;
import com.minosai.scoringapp.model.Group;
import com.minosai.scoringapp.model.ResponseModelPayload;
import com.minosai.scoringapp.model.payload.GroupsPayload;
import com.minosai.scoringapp.ui.event.callback.VoteListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends BaseActivity implements VoteListener {

    @BindView(R.id.event_rv_groups)
    RecyclerView rvGroups;

    GroupAdapter adapter;

    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        ButterKnife.bind(this);

        apiService = ApiClient.getApiService(this);

        fetchData();
    }

    private void fetchData() {

        apiService.fetchGroupsList().enqueue(new Callback<ResponseModelPayload<GroupsPayload>>() {
            @Override
            public void onResponse(Call<ResponseModelPayload<GroupsPayload>> call,
                                   Response<ResponseModelPayload<GroupsPayload>> response) {
                if (!response.isSuccessful()) {
                    showToast(EventActivity.this.getString(R.string.server_error));
                } else {
                    if (!response.body().getMeta().isStatusSuccess()) {
//                        showToast(EventActivity.this.getString(R.string.response_error));
                        showToast(response.body().getMeta().getMessage());
                    } else {
                        updateUI(response.body().getPayload().getGroups());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModelPayload<GroupsPayload>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                showToast(EventActivity.this.getString(R.string.network_error));
            }
        });

    }

    private void updateUI(List<Group> groups) {

        adapter = new GroupAdapter(groups, this);

        rvGroups.setHasFixedSize(true);
        rvGroups.setLayoutManager(new LinearLayoutManager(this));
        rvGroups.setAdapter(adapter);
    }

    @Override
    public void vote(Group group, int score) {
//        apiService.putGroupVote()
    }
}
