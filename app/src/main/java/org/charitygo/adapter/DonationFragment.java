package org.charitygo.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.charitygo.R;
import org.charitygo.model.Donation;

import java.util.ArrayList;

public class DonationFragment extends Fragment {
    //Firebase Database
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();
    private DatabaseReference donationRef = ref.child("donations");
    private ArrayList<Donation> donations = new ArrayList<>();

    //XML Attributes
    private RecyclerView list;

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_donation_hist, container, false);
        list = (RecyclerView) view.findViewById(R.id.donation_view);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this.getContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(mLinearLayoutManager);

        donationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Donation donation = dataSnapshot1.getValue(Donation.class);
                    if(donation.getUserID().equals(firebaseUser.getUid()))
                        donations.add(donation);
                }
                DonationAdapter donationAdapter = new DonationAdapter(donations);
                list.setAdapter(donationAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
