package org.charitygo.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.charitygo.R;

public class RedemptionFragment extends Fragment {
    // Store instance variables
//    private String title;
////    private int page;

    // newInstance constructor for creating fragment with arguments
//    public static RedemptionFragment newInstance(int page, String title) {
//        RedemptionFragment fragmentSecond = new RedemptionFragment();
//        Bundle args = new Bundle();
//        args.putInt("Redemption Page 1", page);
//        args.putString("Redemption Title 1", title);
//        fragmentSecond.setArguments(args);
//        return fragmentSecond;
//    }
//
//    // Store instance variables based on arguments passed
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        page = getArguments().getInt("Redemption Page 2", 0);
//        title = getArguments().getString("Redemption Title 2");
//    }

    private ImageView imageRedemption;
    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_redempt_hist, container, false);
        imageRedemption = (ImageView) view.findViewById(R.id.imageRedempt);
        imageRedemption.setImageDrawable(getResources().getDrawable(R.drawable.lights));
//        TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
//        tvLabel.setText(page + " -- " + title);
        return view;
    }
}
