package org.charitygo;

import android.support.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.charitygo.model.Redeems;
import org.charitygo.model.StepHistory;

public class PointsCalculator {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private DateFormat df = new DateFormat();
    private static long timestamp = System.currentTimeMillis();
    private String dayDatePath = String.valueOf(df.longToYearMonthDay(timestamp));
    private int points;

    public void AvailablePoints() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference();
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        ref.child("stepHistory").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StepHistory steps = dataSnapshot.child(dayDatePath).child(firebaseUser.getUid()).getValue(StepHistory.class);
                points = steps.getSteps() / 10;

                ref.child("vouchers").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            for(DataSnapshot data : dataSnapshot1.getChildren()){
                                Redeems redeem = data.getValue(Redeems.class);
                                if(redeem.getUserID().equals(firebaseUser.getUid()))
                                    points =- redeem.getPrice();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
