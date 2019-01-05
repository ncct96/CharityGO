package org.charitygo.model;

public class User {
    public String name;
    public String email;
    public String contactNumber;
    public String gender;
    public int points = 0;
    public String photoURL;
    public String photoPath;

    public User() {

    }

    public User(String name, String email, String contactNumber, String gender, String photoURL, String photoPath, int points) {
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.gender = gender;
        this.points = points;
        this.photoURL = photoURL;
        this.photoPath = photoPath;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getphotoURL() {
        return photoURL;
    }

    public void setphotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
