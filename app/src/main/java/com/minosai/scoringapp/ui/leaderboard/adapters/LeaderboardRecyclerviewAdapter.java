package com.minosai.scoringapp.ui.leaderboard.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minosai.scoringapp.R;
import com.minosai.scoringapp.model.LeaderboardItem;

import java.util.Collections;
import java.util.List;

public class LeaderboardRecyclerviewAdapter extends RecyclerView.Adapter<LeaderboardRecyclerviewAdapter.ViewHolder> {
    private List<LeaderboardItem> leaderboardItems;

    public LeaderboardRecyclerviewAdapter(List<LeaderboardItem> leaderboardItems) {
        this.leaderboardItems = leaderboardItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_leaderboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Collections.sort(leaderboardItems);
        holder.rank.setText(String.valueOf(position + 1));
        holder.groupName.setText(leaderboardItems.get(position).getGroupName());
        holder.score.setText(String.valueOf(Float.parseFloat(leaderboardItems.get(position).getScore()) * 10));
    }

    @Override
    public int getItemCount() {
        return leaderboardItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView rank;
        TextView groupName;
        TextView score;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            rank = itemView.findViewById(R.id.rank_text);
            groupName = itemView.findViewById(R.id.group_name_text);
            score = itemView.findViewById(R.id.score_text);
        }
    }
}
