package com.minosai.scoringapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minosai.scoringapp.R;
import com.minosai.scoringapp.api.ApiClient;
import com.minosai.scoringapp.api.ApiService;
import com.minosai.scoringapp.base.BaseActivity;
import com.minosai.scoringapp.model.Event;
import com.minosai.scoringapp.model.ResponseModelPayload;
import com.minosai.scoringapp.model.payload.EventsPayload;
import com.minosai.scoringapp.ui.event.EventActivity;
import com.minosai.scoringapp.ui.home.callback.EventClickListener;
import com.minosai.scoringapp.util.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements EventClickListener {

    @BindView(R.id.home_rv_events)
    RecyclerView rvEvents;

    EventAdapter adapter;

    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        apiService = ApiClient.getApiService(this);

        fetchData();
    }

    private void fetchData() {
        apiService.fetchEventsList().enqueue(new Callback<ResponseModelPayload<EventsPayload>>() {
            @Override
            public void onResponse(Call<ResponseModelPayload<EventsPayload>> call,
                                   Response<ResponseModelPayload<EventsPayload>> response) {
                if (!response.isSuccessful()) {
                    showToast(MainActivity.this.getString(R.string.server_error));
                } else {
                    if (!response.body().getMeta().isStatusSuccess()) {
                        showToast(response.body().getMeta().getMessage());
                    } else {
                        updateUI(response.body().getPayload().getEvents());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModelPayload<EventsPayload>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                showToast(MainActivity.this.getString(R.string.network_error));
            }
        });
    }

    private void updateUI(List<Event> events) {
        adapter = new EventAdapter(events, this);

        rvEvents.setHasFixedSize(true);
        rvEvents.setLayoutManager(new LinearLayoutManager(this));
        rvEvents.setAdapter(adapter);
    }

    @Override
    public void onEventClick(Event event) {
        Intent intent = new Intent(this, EventActivity.class);
        intent.putExtra(Constants.EXTRA_EVENT_ID, event.getId());
        intent.putExtra(Constants.EXTRA_EVENT_NAME, event.getEventName());
        startActivity(intent);
    }
}
