package org.charitygo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridLayout;

import org.charitygo.R;

public class RewardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        for (int i = 1; i < 6; i++) {
            GridLayout gridLayout = (GridLayout) findViewById(R.id.reward_cards_grid);
            View view = getLayoutInflater().inflate(R.layout.reward_cards, gridLayout, false);
            gridLayout.addView(view);
        }
    }

}
