package org.charitygo.model;

import java.util.Date;

public class StepHistory {
    public User user;
    public Date startDate;
    public Date endDate;
    public int steps, weeklyPoint;

    public StepHistory(User user, Date startDate, Date endDate, int steps, int weeklyPoint) {
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.steps = steps;
        this.weeklyPoint = weeklyPoint;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getWeeklyPoint() {
        return weeklyPoint;
    }

    public void setWeeklyPoint(int weeklyPoint) {
        this.weeklyPoint = weeklyPoint;
    }
}
