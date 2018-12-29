package org.charitygo.model;

public class Organization {
    private String name;
    private int id, points, drawable;

    public Organization(String name, int id, int points, int drawable) {
        this.name = name;
        this.id = id;
        this.points = points;
        this.drawable = drawable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
