package com.mxt.mysql.masterSlave.model;

public class User {

    private Integer userId;
    private String userName;
    private Integer sex;
    private String userPassword;

    public Integer getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getSex() {
        return sex;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

}