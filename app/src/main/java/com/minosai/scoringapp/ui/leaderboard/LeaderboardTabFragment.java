package com.minosai.scoringapp.ui.leaderboard;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minosai.scoringapp.R;
import com.minosai.scoringapp.api.ApiClient;
import com.minosai.scoringapp.api.ApiService;
import com.minosai.scoringapp.model.LeaderboardItem;
import com.minosai.scoringapp.model.ResponseModelPayload;
import com.minosai.scoringapp.model.payload.LeaderboardPayload;
import com.minosai.scoringapp.ui.leaderboard.adapters.LeaderboardRecyclerviewAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderboardTabFragment extends Fragment {
    private static final String TAG = "LeaderboardTabFragment";

    @BindView(R.id.leaderboard_recyclerview)
    RecyclerView recyclerView;

    private List<LeaderboardItem> leaderboardItems = new ArrayList<>();
    private Unbinder unbinder;

    public LeaderboardTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_leaderboard_tab, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ApiService apiService = ApiClient.getApiService(requireContext());
        LeaderboardRecyclerviewAdapter adapter = new LeaderboardRecyclerviewAdapter(leaderboardItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        String eventId;
        if (getArguments() != null) {
            eventId = getArguments().getString("event_id");
            Call<ResponseModelPayload<LeaderboardPayload>> leaderboardCall = apiService.fetchLeaderboard(eventId);
            leaderboardCall.enqueue(new Callback<ResponseModelPayload<LeaderboardPayload>>() {
                @Override
                public void onResponse(@NonNull Call<ResponseModelPayload<LeaderboardPayload>> call, @NonNull Response<ResponseModelPayload<LeaderboardPayload>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(requireContext(), "Server error", Toast.LENGTH_SHORT).show();
                        try {
                            Log.d(TAG, "onResponse: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    if (!response.body().getMeta().isStatusSuccess()) {
                        Toast.makeText(requireContext(), response.body().getMeta().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        leaderboardItems.clear();
                        for (LeaderboardItem item: response.body().getPayload().getLeaderboardItems()) {
                            if (item.getScore() != null) {
                                leaderboardItems.add(item);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseModelPayload<LeaderboardPayload>> call, @NonNull Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }
            });
        } else {
            Toast.makeText(requireContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
            requireActivity().finish();
        }
    }
}
