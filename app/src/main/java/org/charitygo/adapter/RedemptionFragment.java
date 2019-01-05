package org.charitygo.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.charitygo.R;

public class RedemptionFragment extends Fragment {

    private ImageView imageRedemption;
    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_redempt_hist, container, false);
        imageRedemption = (ImageView) view.findViewById(R.id.imageRedempt);
        imageRedemption.setImageDrawable(getResources().getDrawable(R.drawable.lights));
        return view;
    }
}
