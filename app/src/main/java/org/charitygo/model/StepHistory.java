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
    public User user;
    public Date startDate;
    public Date endDate;
    public int steps, weeklyPoint;

    public StepHistory() {
    }

    public StepHistory(User user, Date startDate, Date endDate, int steps, int weeklyPoint) {
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.steps = steps;
        this.weeklyPoint = weeklyPoint;
    }

    @Exclude
    public User getUser() {
        return user;
    }

    @Exclude
    public void setUser(User user) {
        this.user = user;
    }

    @Exclude
    public Date getStartDate() {
        return startDate;
    }

    @Exclude
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Exclude
    public Date getEndDate() {
        return endDate;
    }

    @Exclude
    public void setEndDate(Date endDate) {
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
