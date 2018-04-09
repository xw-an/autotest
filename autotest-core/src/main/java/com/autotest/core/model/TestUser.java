package com.autotest.core.model;

import java.sql.Timestamp;

public class TestUser {
    private int id;
    private String username;
    private String password;
    private String userId;
    private String role;
    private Timestamp dataChange_CreateTime;
    private Timestamp dataChange_LastTime;

    @Override
    public String toString() {
        return "TestUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userId='" + userId + '\'' +
                ", role='" + role + '\'' +
                ", dataChange_CreateTime=" + dataChange_CreateTime +
                ", dataChange_LastTime=" + dataChange_LastTime +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getDataChange_CreateTime() {
        return dataChange_CreateTime;
    }

    public void setDataChange_CreateTime(Timestamp dataChange_CreateTime) {
        this.dataChange_CreateTime = dataChange_CreateTime;
    }

    public Timestamp getDataChange_LastTime() {
        return dataChange_LastTime;
    }

    public void setDataChange_LastTime(Timestamp dataChange_LastTime) {
        this.dataChange_LastTime = dataChange_LastTime;
    }
}
