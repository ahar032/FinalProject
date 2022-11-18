package com.mahar.busxhacktiv.model;

public class User {
    String uid,fullname,email,phone,picture;
    public User(){}
    public User(String fullname, String email, String phone, String picture,String uid) {
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.picture = picture;
        this.uid=uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
