package com.example.quoctuan.oderfoodserver.model;

/**
 * Created by Admin on 1/9/2018.
 */

public class Users {
    private String name, Password, phone, IsStaff;

    public Users() {
    }

    public Users(String name, String pass, String phone) {
        this.name = name;
        Password = pass;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsStaff() {
        return IsStaff;
    }

    public void setIsStaff(String isStaff) {
        IsStaff = isStaff;
    }
}

