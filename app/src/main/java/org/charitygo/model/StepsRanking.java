package org.charitygo.model;

public class StepsRanking {
    private int accSteps;
    private String name;

    public StepsRanking() {
    }

    public StepsRanking(int accSteps, String name) {
        this.accSteps = accSteps;
        this.name = name;
    }

    public int getAccSteps() {
        return accSteps;
    }

    public void setAccSteps(int accSteps) {
        this.accSteps = accSteps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
