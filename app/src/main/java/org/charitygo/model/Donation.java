package org.charitygo.model;

import java.util.Date;

public class Donation {
    String userID, organizationID, organizationName;
    int points;
    Date transactionDate;

    public Donation() {
    }

    public Donation(String userID, String organizationID, String organizationname, int points, Date transactionDate) {
        transactionDate = new Date();
        this.userID = userID;
        this.organizationID = organizationID;
        this.organizationName = organizationname;
        this.points = points;
        this.transactionDate = transactionDate;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(String organizationID) {
        this.organizationID = organizationID;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
