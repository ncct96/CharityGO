package org.charitygo.model;

import java.util.Calendar;
import java.util.Date;

public class Reward {
    private String key, company, shortDescription, longDescription, code;

    private int validFor, drawable;

    public Reward() {
    }

    public Reward(String company, String shortDescription, String longDescription, String code, int validFor, int drawable) {
        this.company = company;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.code = code;
        this.validFor = validFor;
        this.drawable = drawable;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDates() {

    }

    public int getValidFor() {
        return validFor;
    }

    public void setValidFor(int validFor) {
        this.validFor = validFor;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
