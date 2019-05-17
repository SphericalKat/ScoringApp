package com.minosai.scoringapp.ui.leaderboard;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.minosai.scoringapp.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderboardFragment extends Fragment {

    @BindView(R.id.viewpager_tab)
    SmartTabLayout tabLayout;
    @BindView(R.id.leaderboard_container)
    FrameLayout container;

    public LeaderboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leaderboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
//                getFragmentManager(), FragmentPagerItems.with(requireContext())
//        )
    }
}
