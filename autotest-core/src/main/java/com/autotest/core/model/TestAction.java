package com.autotest.core.model;

import java.sql.Timestamp;

public class TestAction {
    private int id;
    private String actionType;
    private String keyName;
    private Timestamp dataChange_CreateTime;
    private Timestamp dataChange_LastTime;

    @Override
    public String toString() {
        return "TestAction{" +
                "id=" + id +
                ", actionType='" + actionType + '\'' +
                ", keyName='" + keyName + '\'' +
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

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
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
