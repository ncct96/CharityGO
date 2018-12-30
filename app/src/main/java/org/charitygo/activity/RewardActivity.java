package org.charitygo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.charitygo.R;
import org.charitygo.adapter.RewardAdapter;
import org.charitygo.model.Reward;

import java.util.ArrayList;

public class RewardActivity extends AppCompatActivity {

    static ArrayList<Reward> rewardList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView list = (RecyclerView) findViewById(R.id.reward_view);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(RewardActivity.this, 2);
        list.setLayoutManager(mGridLayoutManager);
        getRewards();
        RewardAdapter rewardAdapter = new RewardAdapter(rewardList);
        list.setAdapter(rewardAdapter);
    }

    private void getRewards(){
        rewardList.add(new Reward("Reward 1", R.drawable.borger));
        rewardList.add(new Reward("Reward 2", R.drawable.borger));
        rewardList.add(new Reward("Reward 3", R.drawable.borger));
        rewardList.add(new Reward("Reward 4", R.drawable.borger));
        rewardList.add(new Reward("Reward 5", R.drawable.borger));
        rewardList.add(new Reward("Reward 6", R.drawable.borger));
    }

    public void goToVoucher(View view) {
        Intent intent = new Intent(this, VoucherActivity.class);
        intent.putExtra("EXTRA_ID", String.valueOf(view.getId()));
        startActivity(intent);
    }
}
