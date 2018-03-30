package com.autotest.core.model;

import java.sql.Timestamp;

public class TestCase {
    private int id;
    private String testScenariosName;
    private String testScenariosType;
    private String testContent;
    private String remark;
    private String userId;
    private Timestamp dataChange_CreateTime;
    private Timestamp dataChange_LastTime;

    @Override
    public String toString() {
        return "TestCase{" +
                "id=" + id +
                ", testScenariosName='" + testScenariosName + '\'' +
                ", testScenariosType='" + testScenariosType + '\'' +
                ", testContent='" + testContent + '\'' +
                ", remark='" + remark + '\'' +
                ", userId='" + userId + '\'' +
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

    public String getTestScenariosName() {
        return testScenariosName;
    }

    public void setTestScenariosName(String testScenariosName) {
        this.testScenariosName = testScenariosName;
    }

    public String getTestScenariosType() {
        return testScenariosType;
    }

    public void setTestScenariosType(String testScenariosType) {
        this.testScenariosType = testScenariosType;
    }

    public String getTestContent() {
        return testContent;
    }

    public void setTestContent(String testContent) {
        this.testContent = testContent;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
