package com.example.demo.model;

import java.util.Date;

public class User {
    String id;
    String fullName;
    Date dob;
    String contact;
    String password;


    public User(String id, String fullName, Date dob, String contact, String password) {
        this.id = id;
        this.fullName = fullName;
        this.dob = dob;
        this.contact = contact;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
