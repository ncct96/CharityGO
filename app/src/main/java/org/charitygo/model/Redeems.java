package org.charitygo.model;

import java.util.Calendar;
import java.util.Date;

public class Redeems {
    String key, userID, rewardsID;
    private Date redeemDate, expiryDate;

    public Redeems() {
    }

    public Redeems(String userID, String rewardsID, Date redeemDate, Date expiryDate, int validFor) {
        this.userID = userID;
        this.rewardsID = rewardsID;

        Calendar calendar = Calendar.getInstance();
        this.redeemDate = calendar.getTime();
        calendar.add(Calendar.MINUTE, validFor);
        this.expiryDate = calendar.getTime();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRewardsID() {
        return rewardsID;
    }

    public void setRewardsID(String rewardsID) {
        this.rewardsID = rewardsID;
    }

    public Date getRedeemDate() {
        return redeemDate;
    }

    public void setRedeemDate(Date redeemDate) {
        this.redeemDate = redeemDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
