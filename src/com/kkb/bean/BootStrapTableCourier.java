package com.kkb.bean;

public class BootStrapTableCourier {
    private int id;
    private String name;
    private String phone;
    private String password;
    private String idCard;
    private int pieces;
    private String createTime;
    private String loginTime;

    public BootStrapTableCourier() {
    }

    public BootStrapTableCourier(int id, String name, String phone, String password, String idCard, int pieces, String createTime, String loginTime) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.idCard = idCard;
        this.pieces = pieces;
        this.createTime = createTime;
        this.loginTime = loginTime;
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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }
}
