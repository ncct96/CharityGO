package org.charitygo.model;

import java.util.Calendar;
import java.util.Date;

public class Redeems {
    String key, userID, rewardsID, rewardDesc;
    private Date redeemDate, expiryDate;
    private int price;

    public Redeems() {
    }

    public Redeems(String userID, String rewardsID, String company, String desc, int validFor, int price) {
        this.userID = userID;
        this.rewardsID = rewardsID;
        this.price = price;
        rewardDesc = company + " - " + desc;

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

    public String getRewardDesc() {
        return rewardDesc;
    }

    public void setRewardDesc(String rewardDesc) {
        this.rewardDesc = rewardDesc;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
