package org.charitygo.model;

public class StepsRanking {
    private int accSteps;
    private String uid;


    public StepsRanking() {
    }

    public StepsRanking(int accSteps, String uid) {
        this.accSteps = accSteps;
        this.uid = uid;
    }

    public int getAccSteps() {
        return accSteps;
    }

    public void setAccSteps(int accSteps) {
        this.accSteps = accSteps;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
