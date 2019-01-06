package org.charitygo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.charitygo.R;
import org.charitygo.model.LeaderInfo;
import org.charitygo.model.StepsRanking;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardHolder> {

    private Context context;
    private List<StepsRanking> rankList;

    public LeaderboardAdapter(Context context, List<StepsRanking> rankList) {
        this.context = context;
        this.rankList = rankList;
    }

    @Override
    public int getItemCount() {
        return rankList.size();
    }

    @Override
    public LeaderboardHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View userView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.leaderboard_card, viewGroup, false);

        return new LeaderboardHolder(userView);
    }

    @Override
    public void onBindViewHolder(LeaderboardHolder leaderboardHolder, int i) {
        final StepsRanking lb = rankList.get(i);
        leaderboardHolder.leaderRank.setImageResource(R.drawable.circle);
        leaderboardHolder.leaderName.setText(lb.getName());
        leaderboardHolder.leaderSteps.setText(String.valueOf(lb.getAccSteps()));
    }

    public static class LeaderboardHolder extends RecyclerView.ViewHolder {
        protected ImageView leaderRank;
        protected TextView leaderName;
        protected TextView leaderSteps;

        public LeaderboardHolder(View v) {
            super(v);
            leaderRank = (ImageView) v.findViewById(R.id.leaderRank);
            leaderName = (TextView) v.findViewById(R.id.leaderName);
            leaderSteps = (TextView) v.findViewById(R.id.leaderSteps);
        }


    }
}
