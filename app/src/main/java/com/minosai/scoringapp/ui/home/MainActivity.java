package com.minosai.scoringapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.minosai.scoringapp.R;
import com.minosai.scoringapp.api.ApiClient;
import com.minosai.scoringapp.base.BaseActivity;
import com.minosai.scoringapp.model.Event;
import com.minosai.scoringapp.model.ResponseModelPayload;
import com.minosai.scoringapp.model.payload.EventsPayload;
import com.minosai.scoringapp.ui.home.bottomsheet.SettingsBottomSheetFragment;
import com.minosai.scoringapp.ui.home.bottomsheet.VotingBottomSheetFragnent;
import com.minosai.scoringapp.ui.home.callback.EventClickListener;
import com.minosai.scoringapp.ui.leaderboard.LeaderboardActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements EventClickListener {

    @BindView(R.id.home_rv_events)
    RecyclerView rvEvents;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    EventAdapter adapter;
    List<Event> events = new ArrayList<>();
    Gson gson = new Gson();

    @OnClick(R.id.about_us)
    void onClickAbout() {
        navigate(AttribouterActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        apiService = ApiClient.getApiService(this);

        fetchData();

        swipeRefreshLayout.setOnRefreshListener(this::fetchData);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorBlue);
    }

    private void fetchData() {
        swipeRefreshLayout.setRefreshing(true);
        apiService.fetchEventsList().enqueue(new Callback<ResponseModelPayload<EventsPayload>>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModelPayload<EventsPayload>> call, @NonNull Response<ResponseModelPayload<EventsPayload>> response) {
                if (!response.isSuccessful()) {
                    showToast(MainActivity.this.getString(R.string.server_error));
                    try {
                        Log.d(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                if (!response.body().getMeta().isStatusSuccess()) {
                    showToast(response.body().getMeta().getMessage());
                } else {
                    events = response.body().getPayload().getEvents();
                    updateUI(events);
                }
            }

            @Override
            public void onFailure(Call<ResponseModelPayload<EventsPayload>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                showToast(MainActivity.this.getString(R.string.network_error));
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void updateUI(List<Event> events) {
        adapter = new EventAdapter(events, this);

        rvEvents.setHasFixedSize(true);
        rvEvents.setLayoutManager(new LinearLayoutManager(this));
        rvEvents.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onEventClick(Event event) {
        VotingBottomSheetFragnent votingBottomSheetFragnent
                = VotingBottomSheetFragnent.newInstance(event.getId());
        votingBottomSheetFragnent.setCancelable(false);
        votingBottomSheetFragnent.show(
                getSupportFragmentManager(),
                votingBottomSheetFragnent.getTag()
        );
    }

    @OnClick(R.id.home_button_settings)
    void settingsOnClick() {
        SettingsBottomSheetFragment settingsBottomSheetFragment
                = SettingsBottomSheetFragment.getInstance();
        settingsBottomSheetFragment.show(
                getSupportFragmentManager(),
                settingsBottomSheetFragment.getTag()
        );
    }

    @OnClick(R.id.home_button_leaderboard)
    void leaderBoardOnClick() {
        if (events.isEmpty()) {
            showToast("There aren't any events!");
        } else {
            String json = gson.toJson(events);
            Intent intent = new Intent(MainActivity.this, LeaderboardActivity.class);
            intent.putExtra("event_json", json);
            startActivity(intent);
        }
    }
}
