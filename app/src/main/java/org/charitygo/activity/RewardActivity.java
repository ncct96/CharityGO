package org.charitygo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.charitygo.R;
import org.charitygo.adapter.RewardAdapter;
import org.charitygo.model.Reward;

import java.util.ArrayList;

public class RewardActivity extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();
    private DatabaseReference rewardRef = ref.child("rewards");
    static ArrayList<Reward> rewardList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //initRewards();
        rewardList.clear();
        createViews();
    }

    public void initRewards() {
        rewardRef.push().setValue(new Reward("Mc Ronalds", "Free Borger", "Redeem a free borger upon a purchase of RM30 and above at any Mc Ronalds outlet in Malaysia", "MR2003", 10, R.drawable.borger, 200));
        rewardRef.push().setValue(new Reward("Zarora", "10% discount", "Get a 10% discount when purchasing above RM100 in a single receipt", "ZLR34B23", 7200, R.drawable.zalora, 50));
        rewardRef.push().setValue(new Reward("Mc Ronalds", "Extra Dog Food", "Get a free 500g bag of Kibbles Dog Food upon a purchase of a 2kg bag of Kibbles Dog Food", "KB23-B-53", 7200, R.drawable.borger, 20));
        rewardRef.push().setValue(new Reward("Nya!", "Nya", "Nya nya nya nya nya nya nya nya nya nya nya nya nya nya!", "NYA", 1, R.drawable.borger, 0));
    }

    private void createViews(){
        rewardList.clear();
        rewardRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String key = dataSnapshot1.getKey();
                    Reward reward = dataSnapshot1.getValue(Reward.class);
                    reward.setKey(key);
                    rewardList.add(reward);
                }

                RecyclerView list = (RecyclerView) findViewById(R.id.reward_view);
                GridLayoutManager mGridLayoutManager = new GridLayoutManager(RewardActivity.this, 2);
                list.setLayoutManager(mGridLayoutManager);
                RewardAdapter rewardAdapter = new RewardAdapter(RewardActivity.this, rewardList);
                list.setAdapter(rewardAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void goToVoucher(View view) {
        Intent intent = new Intent(this, VoucherActivity.class);
        intent.putExtra("EXTRA_ID", String.valueOf(view.getId()));
        startActivity(intent);
    }
}
