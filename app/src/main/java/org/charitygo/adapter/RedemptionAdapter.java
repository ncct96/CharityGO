package org.charitygo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.charitygo.R;

public class RedemptionAdapter extends RecyclerView.Adapter <RedemptionAdapter.RedemptionHolder>{

    @NonNull
    @Override
    public RedemptionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RedemptionHolder redemptionHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class RedemptionHolder extends RecyclerView.ViewHolder {
        protected ImageView background;
        protected TextView name;
        protected TextView points;
        protected Button donate;
        protected ImageButton more;

        public RedemptionHolder(View v) {
            super(v);
            background = v.findViewById(R.id.info_image);
            name = v.findViewById(R.id.info_text);
            points = v.findViewById(R.id.info_points);
            donate = v.findViewById(R.id.info_donate);
            more = v.findViewById(R.id.info_more);
        }
    }
}
