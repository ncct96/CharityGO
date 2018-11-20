package org.charitygo.model;

public class LeaderInfo {
    protected int rank;
    protected String name;
    protected int steps;
    protected static final String NAME_PREFIX = "Name_";
    protected static final String SURNAME_PREFIX = "Surname_";
    protected static final String EMAIL_PREFIX = "email_";

    public LeaderInfo(int rank, String name, int steps) {
        this.rank = rank;
        this.name = name;
        this.steps = steps;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }
}
