package com.autotest.core.model;

import java.sql.Timestamp;

public class TestLog {

    private int id;
    private int result_id;
    private int case_id;
    private int step_id;
    private String runParams;
    private String logContent;
    private String remark;
    private Timestamp dataChange_CreateTime;
    private Timestamp dataChange_LastTime;

    @Override
    public String toString() {
        return "TestLog{" +
                "id=" + id +
                ", result_id=" + result_id +
                ", case_id=" + case_id +
                ", step_id=" + step_id +
                ", runParams='" + runParams + '\'' +
                ", logContent='" + logContent + '\'' +
                ", remark='" + remark + '\'' +
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

    public int getResult_id() {
        return result_id;
    }

    public void setResult_id(int result_id) {
        this.result_id = result_id;
    }

    public int getCase_id() {
        return case_id;
    }

    public void setCase_id(int case_id) {
        this.case_id = case_id;
    }

    public int getStep_id() {
        return step_id;
    }

    public void setStep_id(int step_id) {
        this.step_id = step_id;
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
