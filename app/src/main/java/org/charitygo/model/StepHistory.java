package org.charitygo.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class StepHistory {
    public int steps, point;
    public String key;

    public StepHistory() {
    }

    public StepHistory(int steps, int point, String key) {
        this.steps = steps;
        this.key = key;
        this.point = point;
    }

    @Exclude
    public int getSteps() {
        return steps;
    }

    @Exclude
    public void setSteps(int steps) {
        this.steps = steps;
    }

    @Exclude
    public int getPoint() {
        return point;
    }

    @Exclude
    public void setPoint(int point) {
        this.point = point;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
