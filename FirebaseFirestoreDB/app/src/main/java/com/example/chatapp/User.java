package com.example.chatapp;

public class User {
    private String id;
    private String name;
    private String email;
    private String gender;
    private String dob;
    private String photoUrl;

    public User() {
    }

    public User(String id, String name, String email, String gender, String dob, String photoUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.dob = dob;
        this.photoUrl = photoUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
