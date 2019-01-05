package org.charitygo.adapter;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.charitygo.R;

public class DonationFragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
//    public static DonationFragment newInstance(int page, String title) {
//        DonationFragment fragmentFirst = new DonationFragment();
//        Bundle args = new Bundle();
//        args.putInt("Donation Page 1", page);
//        args.putString("Donation Title 1", title);
//        fragmentFirst.setArguments(args);
//        return fragmentFirst;
//    }

//    // Store instance variables based on arguments passed
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        page = getArguments().getInt("Donation Page 2", 0);
//        title = getArguments().getString("Donation Title 2");
//    }
    private ImageView imageDonation;

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_transact_hist, container, false);
        //TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
        //tvLabel.setText(page + " -- " + title);
        imageDonation = (ImageView) view.findViewById(R.id.imageTransact);
        imageDonation.setImageDrawable(getResources().getDrawable(R.drawable.stars));
        return view;
    }
}
