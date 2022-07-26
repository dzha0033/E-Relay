package com.kkb.bean;

import java.sql.Timestamp;

public class Users {
    private int id;
    private String name;
    private String phone;
    private String password;
    private String idCard;
    private Timestamp createTime;
    private Timestamp loginTime;

    public Users(int id, String name, String phone, String password,String idCard, Timestamp createTime, Timestamp loginTime) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.createTime = createTime;
        this.loginTime = loginTime;
        this.idCard = idCard;
    }

    public Users() {
    }

    public Users(String name, String phone, String password, String idCard) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.idCard = idCard;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }


    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
