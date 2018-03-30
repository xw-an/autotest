package com.autotest.core.model;

import java.util.Map;

public class TestStepExec {
    private int stepId;
    private String stepName;
    private String actionType;
    private Map<String,String> actionMap;

    @Override
    public String toString() {
        return "TestStepExec{" +
                "stepId=" + stepId +
                ", stepName='" + stepName + '\'' +
                ", actionType='" + actionType + '\'' +
                ", actionMap=" + actionMap +
                '}';
    }

    public int getstepId() {
        return stepId;
    }

    public void setstepId(int stepId) {
        this.stepId = stepId;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Map<String, String> getActionMap() {
        return actionMap;
    }

    public void setActionMap(Map<String, String> actionMap) {
        this.actionMap = actionMap;
    }
}
