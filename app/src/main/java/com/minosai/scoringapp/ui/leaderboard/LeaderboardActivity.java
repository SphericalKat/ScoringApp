package com.minosai.scoringapp.ui.leaderboard;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minosai.scoringapp.R;
import com.minosai.scoringapp.api.ApiClient;
import com.minosai.scoringapp.api.ApiService;
import com.minosai.scoringapp.base.BaseActivity;
import com.minosai.scoringapp.model.Event;
import com.minosai.scoringapp.model.LeaderboardItem;
import com.minosai.scoringapp.model.ResponseModelPayload;
import com.minosai.scoringapp.model.payload.GlobalLeaderboardPayload;
import com.minosai.scoringapp.model.requestbody.GlobalLeaderboardItem;
import com.minosai.scoringapp.ui.home.MainActivity;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaderboardActivity extends BaseActivity {
    @BindView(R.id.viewpager_leaderboard)
    ViewPager viewPager;

    @BindView(R.id.viewpager_tab)
    SmartTabLayout viewPagerTab;

    List<Event> events;
    List<GlobalLeaderboardItem> globalLeaderboardItems;
    Map<String, String> groupScoresMap = new HashMap<>();
    List<LeaderboardItem> leaderboardItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        ButterKnife.bind(this);
        ApiService apiService = ApiClient.getApiService(this);
        Call<ResponseModelPayload<GlobalLeaderboardPayload>> globalCall = apiService.fetchGlobalLeaderboard();

        globalCall.enqueue(new Callback<ResponseModelPayload<GlobalLeaderboardPayload>>() {
            @Override
            public void onResponse(@NonNull Call<ResponseModelPayload<GlobalLeaderboardPayload>> call, @NonNull Response<ResponseModelPayload<GlobalLeaderboardPayload>> response) {
                if (!response.isSuccessful()) {
                    showToast(LeaderboardActivity.this.getString(R.string.server_error));
                    return;
                }
                if (!response.body().getMeta().isStatusSuccess()) {
                    showToast(response.body().getMeta().getMessage());
                } else {
                    globalLeaderboardItems = response.body().getPayload().getGlobalLeaderboardItems();
                    events = getEventsFromJson(getIntent().getStringExtra("event_json"));
                    for (GlobalLeaderboardItem item : globalLeaderboardItems) {
                        for (LeaderboardItem leaderboardItem : item.getLeaderboard()) {
                            if (!groupScoresMap.containsKey(leaderboardItem.getGroupName())) {
                                groupScoresMap.put(leaderboardItem.getGroupName(), leaderboardItem.getScore());
                            } else {
                                float prevScore = Float.parseFloat(Objects.requireNonNull(groupScoresMap.get(leaderboardItem.getGroupName())));
                                groupScoresMap.put(leaderboardItem.getGroupName(), String.valueOf(prevScore + Float.parseFloat(leaderboardItem.getScore())));
                            }
                        }
                    }

                    for (Map.Entry<String, String> item : groupScoresMap.entrySet()) {
                        groupScoresMap.put(item.getKey(), String.valueOf(Float.parseFloat(item.getValue())/events.size()));
                    }

                    for (Map.Entry<String, String> item : groupScoresMap.entrySet()) {
                        LeaderboardItem leaderboardItem = new LeaderboardItem();
                        leaderboardItem.setGroupName(item.getKey());
                        leaderboardItem.setScore(item.getValue());
                        leaderboardItems.add(leaderboardItem);
                    }


                    FragmentPagerItems.Creator creator = FragmentPagerItems.with(LeaderboardActivity.this);
                    Gson gson = new Gson();
                    String json = gson.toJson(leaderboardItems);
                    creator.add("Global", GlobalLeaderboardFragment.class, new Bundler().putString("leader_board", json).get());
                    for (Event item : events) {
                        creator = creator.add(item.getEventName(), LeaderboardTabFragment.class, new Bundler().putString("event_id", item.getId()).get());
                    }
                    FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), creator.create());


                    viewPager.setAdapter(adapter);
                    viewPagerTab.setViewPager(viewPager);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseModelPayload<GlobalLeaderboardPayload>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }

    private List<Event> getEventsFromJson(String jsonString) {
        Gson gson = new Gson();
        Type eventType = new TypeToken<List<Event>>() {
        }.getType();
        return gson.fromJson(jsonString, eventType);
    }


}
