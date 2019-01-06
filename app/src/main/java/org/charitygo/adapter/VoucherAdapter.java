package org.charitygo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.charitygo.R;
import org.charitygo.model.Redeems;

import java.text.SimpleDateFormat;
import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherHolder> {
    private List<Redeems> voucherList;

    public VoucherAdapter(List<Redeems> voucherList) {
        this.voucherList = voucherList;
    }

    @Override
    public int getItemCount() {
        return voucherList.size();
    }

    @Override
    public VoucherAdapter.VoucherHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View userView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_card, viewGroup, false);

        return new VoucherAdapter.VoucherHolder(userView);
    }

    @Override
    public void onBindViewHolder(VoucherAdapter.VoucherHolder voucherHolder, int i) {
        Redeems voucher = voucherList.get(i);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        voucherHolder.time.setText(sdf.format(voucher.getRedeemDate()));
        voucherHolder.points.setText(String.valueOf(voucher.getPrice()));
        voucherHolder.desc.setText(voucher.getRewardDesc());
    }

    public static class VoucherHolder extends RecyclerView.ViewHolder {
        protected TextView time;
        protected TextView points;
        protected TextView desc;

        public VoucherHolder(View v) {
            super(v);
            time = v.findViewById(R.id.history_date);
            points = v.findViewById(R.id.history_points);
            desc = v.findViewById(R.id.history_action);
        }
    }
}
