package com.autotest.core.model;

import java.util.List;

public class TestSuit {
    List<Integer> caseIds;
    String userId;

    @Override
    public String toString() {
        return "TestSuit{" +
                "caseIds=" + caseIds +
                ", userId='" + userId + '\'' +
                '}';
    }

    public List<Integer> getCaseIds() {
        return caseIds;
    }

    public void setCaseIds(List<Integer> caseIds) {
        this.caseIds = caseIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
