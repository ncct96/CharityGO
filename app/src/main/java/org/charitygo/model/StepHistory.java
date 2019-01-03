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
    public String uid;
    public long startDate;
    public long endDate;
    public int steps, weeklyPoint;

    public StepHistory() {
    }

    public StepHistory(String uid, long startDate, long endDate, int steps, int weeklyPoint) {
        this.uid = uid;
        this.startDate = startDate;
        this.endDate = endDate;
        this.steps = steps;
        this.weeklyPoint = weeklyPoint;
    }

    @Exclude
    public String getUid() {
        return uid;
    }

    @Exclude
    public void setUid(String uid) {
        this.uid = uid;
    }

    @Exclude
    public long getStartDate() {
        return startDate;
    }

    @Exclude
    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    @Exclude
    public long getEndDate() {
        return endDate;
    }

    @Exclude
    public void setEndDate(long endDate) {
        this.endDate = endDate;
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
    public int getWeeklyPoint() {
        return weeklyPoint;
    }

    @Exclude
    public void setWeeklyPoint(int weeklyPoint) {
        this.weeklyPoint = weeklyPoint;
    }

}
