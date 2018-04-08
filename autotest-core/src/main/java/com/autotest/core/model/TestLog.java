package com.autotest.core.model;

import java.sql.Timestamp;

public class TestLog {
    private int id;
    private String result_id;
    private String className;
    private String methodName;
    private String runParams;
    private String logContent;
    private String remark;
    private String logLevel;
    private Timestamp dataChange_CreateTime;
    private Timestamp dataChange_LastTime;

    @Override
    public String toString() {
        return "TestLog{" +
                "id=" + id +
                ", result_id='" + result_id + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", runParams='" + runParams + '\'' +
                ", logContent='" + logContent + '\'' +
                ", remark='" + remark + '\'' +
                ", logLevel='" + logLevel + '\'' +
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

    public String getResult_id() {
        return result_id;
    }

    public void setResult_id(String result_id) {
        this.result_id = result_id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getRunParams() {
        return runParams;
    }

    public void setRunParams(String runParams) {
        this.runParams = runParams;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
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
