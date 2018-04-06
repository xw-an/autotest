package com.autotest.core.model;

import java.sql.Timestamp;

public class TestResultCase {

    private int id;
    private int caseId;
    private String userId;
    private String testScenariosName;
    private String testScenariosType;
    private String result;

    @Override
    public String toString() {
        return "TestResultCase{" +
                "id=" + id +
                ", caseId=" + caseId +
                ", userId='" + userId + '\'' +
                ", testScenariosName='" + testScenariosName + '\'' +
                ", testScenariosType='" + testScenariosType + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
