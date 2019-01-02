package org.charitygo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.charitygo.R;
import org.charitygo.activity.VoucherActivity;
import org.charitygo.model.Reward;

import java.util.List;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.RewardHolder> {
    private final Context context;
    private List<Reward> rewardList;

    public RewardAdapter(Context context, List<Reward> rewardList) {
        this.context = context;
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
        rewardHolder.desc.setText(reward.getShortDescription());
        rewardHolder.background.setImageResource(reward.getDrawable());

        final String id = reward.getKey();
        rewardHolder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(context, VoucherActivity.class);
                intent.putExtra("EXTRA_ID", id);
                context.startActivity(intent);
            }
        });
    }

    public static class RewardHolder extends RecyclerView.ViewHolder {
        protected ImageView background;
        protected TextView desc;

        public RewardHolder(View v) {
            super(v);
            desc = v.findViewById(R.id.reward_desc);
            background = (ImageView) v.findViewById(R.id.reward_image);
        }
    }
}
