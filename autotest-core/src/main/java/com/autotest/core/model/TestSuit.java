package com.autotest.core.model;

import java.util.List;

public class TestSuit {
    List<Integer> caseIds;

    @Override
    public String toString() {
        return "TestSuit{" +
                "caseIds=" + caseIds +
                '}';
    }

    public List<Integer> getCaseIds() {
        return caseIds;
    }

    public void setCaseIds(List<Integer> caseIds) {
        this.caseIds = caseIds;
    }
}
