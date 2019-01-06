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
import org.charitygo.model.Donation;
import org.charitygo.model.Reward;

import java.text.SimpleDateFormat;
import java.util.List;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.DonationHolder> {
    private List<Donation> donationList;

    public DonationAdapter(List<Donation> donationList) {
        this.donationList = donationList;
    }

    @Override
    public int getItemCount() {
        return donationList.size();
    }

    @Override
    public DonationAdapter.DonationHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View userView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_card, viewGroup, false);

        return new DonationAdapter.DonationHolder(userView);
    }

    @Override
    public void onBindViewHolder(DonationAdapter.DonationHolder donationHolder, int i) {
        Donation donation = donationList.get(i);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        donationHolder.time.setText(sdf.format(donation.getTransactionDate()));
        donationHolder.points.setText(donation.getPoints());
        donationHolder.desc.setText(donation.getOrganizationName() + " - ");
    }

    public static class DonationHolder extends RecyclerView.ViewHolder {
        protected TextView time;
        protected TextView points;
        protected TextView desc;

        public DonationHolder(View v) {
            super(v);
            time = v.findViewById(R.id.history_date);
            points = v.findViewById(R.id.history_points);
            desc = v.findViewById(R.id.history_action);
        }
    }
}
