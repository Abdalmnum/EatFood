package com.example.eatfoodv2.Model;

public class UserModel {
    private String uid,name,addresse,phone;

    public UserModel(String uid, String name, String addresse, String phone) {
        this.uid = uid;
        this.name = name;
        this.addresse = addresse;
        this.phone = phone;
    }

    public UserModel() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
