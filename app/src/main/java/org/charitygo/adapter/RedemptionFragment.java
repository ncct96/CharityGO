package org.charitygo.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.charitygo.R;

public class RedemptionFragment extends Fragment {
    //Firebase Database
    private FirebaseUser currentUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference userRedeem = FirebaseDatabase.getInstance().getReference();

    private ImageView imageRedemption;
    private RecyclerView redemptionList;
    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_redempt_hist, container, false);
        imageRedemption = (ImageView) view.findViewById(R.id.imageRedempt);
        imageRedemption.setImageDrawable(getResources().getDrawable(R.drawable.lights));
        redemptionList = (RecyclerView) view.findViewById(R.id.redemption_view);

//        userRedeem.child("redemptions").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
    }
}
