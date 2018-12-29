package org.charitygo.model;

public class Reward {
    private String description;
    private int drawable;

    public Reward(String description, int drawable){
        this.description = description;
        this.drawable = drawable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
