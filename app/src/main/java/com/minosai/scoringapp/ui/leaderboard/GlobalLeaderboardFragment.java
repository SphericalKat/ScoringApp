package com.minosai.scoringapp.ui.leaderboard;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minosai.scoringapp.R;
import com.minosai.scoringapp.model.LeaderboardItem;
import com.minosai.scoringapp.ui.leaderboard.adapters.LeaderboardRecyclerviewAdapter;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class GlobalLeaderboardFragment extends Fragment {
    private Unbinder unbinder;
    private List<LeaderboardItem> leaderboardItems;

    @BindView(R.id.leaderboard_recyclerview)
    RecyclerView recyclerView;


    public GlobalLeaderboardFragment() {
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
        if (getArguments() != null) {
            Type type = new TypeToken<List<LeaderboardItem>>() {
            }.getType();
            Gson gson = new Gson();
            leaderboardItems = gson.fromJson(getArguments().getString("leader_board"), type);

            LeaderboardRecyclerviewAdapter adapter = new LeaderboardRecyclerviewAdapter(leaderboardItems);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        }
    }
}
