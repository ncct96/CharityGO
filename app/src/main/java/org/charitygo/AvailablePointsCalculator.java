package org.charitygo;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.charitygo.model.StepHistory;
import org.charitygo.model.User;

import java.util.Date;

public class AvailablePointsCalculator {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference();
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference stepRef = ref.child("rewards/"+ firebaseUser.getUid());
    private DatabaseReference userRef = ref.child("users/"+ firebaseUser.getUid());
    private StepHistory currentSteps = new StepHistory();
    private User user = new User();
    private int points = 0;

    public int calculatePoints(){
        points = 0;
        stepRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    currentSteps = dataSnapshot.getValue(StepHistory.class);

/*                    if(currentSteps.getStartDate() == new Date()){
                        points = currentSteps.getSteps() / 10;
                        return;
                    }*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return points;
    }

    public int getRedeemPoints(){
        points = 0;
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                points = user.getPoints();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return points;
    }
}
