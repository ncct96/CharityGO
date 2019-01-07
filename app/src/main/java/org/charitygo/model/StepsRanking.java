package org.charitygo.model;

public class StepsRanking {
    private int accSteps;
    private String name, key;

    public StepsRanking() {
    }

    public StepsRanking(int accSteps, String name, String key) {
        this.accSteps = accSteps;
        this.name = name;
        this.key = key;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
