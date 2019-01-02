package org.charitygo.model;

public class User {
    public String name;
    public String email;
    public String contactNumber;
    public String gender;
    public int points = 0;

    public User() {

    }

    public User(String name, String email, String contactNumber, String gender, int points) {
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.gender = gender;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
