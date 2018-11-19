package org.charitygo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.charitygo.R;
import org.charitygo.model.LeaderInfo;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardHolder> {


    private List<LeaderInfo> leaderList;

    public LeaderboardAdapter(List<LeaderInfo> leaderList) {
        this.leaderList = leaderList;
    }

    @Override
    public int getItemCount() {
        return leaderList.size();
    }

    @Override
    public LeaderboardHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View userView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.leaderboard_card, viewGroup, false);

        return new LeaderboardHolder(userView);
    }

    @Override
    public void onBindViewHolder(LeaderboardHolder leaderboardHolder, int i) {
        LeaderInfo lb = leaderList.get(i);
        leaderboardHolder.leaderRank.setText(String.valueOf(lb.getRank()));
        leaderboardHolder.leaderName.setText(lb.getName());
        leaderboardHolder.leaderSteps.setText(String.valueOf(lb.getSteps()));
    }

    public static class LeaderboardHolder extends RecyclerView.ViewHolder {
        protected TextView leaderRank;
        protected TextView leaderName;
        protected TextView leaderSteps;

        public LeaderboardHolder(View v) {
            super(v);
            leaderRank = (TextView) v.findViewById(R.id.leaderRank);
            leaderName = (TextView) v.findViewById(R.id.leaderName);
            leaderSteps = (TextView) v.findViewById(R.id.leaderSteps);
        }


    }
}
