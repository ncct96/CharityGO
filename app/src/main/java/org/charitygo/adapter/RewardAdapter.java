package org.charitygo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.charitygo.R;
import org.charitygo.model.Reward;

import java.util.List;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.RewardHolder> {
    private List<Reward> rewardList;

    public RewardAdapter(List<Reward> rewardList) {
        this.rewardList = rewardList;
    }

    @Override
    public int getItemCount() {
        return rewardList.size();
    }

    @Override
    public RewardAdapter.RewardHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View userView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reward_cards, viewGroup, false);

        return new RewardHolder(userView);
    }

    @Override
    public void onBindViewHolder(RewardAdapter.RewardHolder rewardHolder, int i) {
        Reward reward = rewardList.get(i);
        rewardHolder.background.setImageResource(reward.getDrawable());
        rewardHolder.description.setText(reward.getDescription());
    }

    public static class RewardHolder extends RecyclerView.ViewHolder {
        protected ImageView background;
        protected TextView description;

        public RewardHolder(View v) {
            super(v);
            background = (ImageView) v.findViewById(R.id.reward_image);
            description = (TextView) v.findViewById(R.id.reward_desc);
        }
    }
}
