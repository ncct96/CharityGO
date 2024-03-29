package org.charitygo.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.charitygo.R;

public class SponsorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.sponToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createSponsors();
    }

    protected void createSponsors() {
        String[] sponsorName = {"Sea", "Stars", "McDonald", "Zalora"};
        Integer[] sponsorImage = {R.drawable.sea, R.drawable.stars, R.drawable.borger, R.drawable.zalora};
        for (int i = 0; i <= 3; i++) {
            LinearLayout spon = (LinearLayout) findViewById(R.id.sponsor_linear);
            View view = getLayoutInflater().inflate(R.layout.sponsor, spon, false);

            TextView tv = (TextView) view.findViewById(R.id.sponsorName);
            ImageView iv = (ImageView) view.findViewById(R.id.sponsorPic);
            tv.setText(sponsorName[i]);
            iv.setImageResource(sponsorImage[i]);
//            tv.setId(i);

            spon.addView(view);
        }
    }

    public void navigateSponsor(View view) {
        Intent intent = new Intent(this, SponsorInfoActivity.class);
        startActivity(intent);
    }
}
