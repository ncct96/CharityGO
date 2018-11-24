package org.charitygo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SpnsorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spnsor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.sponToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createSponsors();
    }

    protected void createSponsors(){

        for(int i = 0; i <= 3; i++){
            LinearLayout spon = (LinearLayout) findViewById(R.id.sponsor_linear);
            View view = getLayoutInflater().inflate(R.layout.sponsor, spon, false);

            TextView tv = (TextView) view.findViewById(R.id.sponsorName);
            tv.setText(tv.getText().toString() + " " + i);
            tv.setId(i);

            spon.addView(view);
        }
    }

    public void navigateSponsor(View view) {
        Intent intent = new Intent(this, SponsorInfoActivity.class);
        startActivity(intent);
    }
}
