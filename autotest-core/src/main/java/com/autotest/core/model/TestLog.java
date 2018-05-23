package com.autotest.core.model;

import lombok.Data;
import java.sql.Timestamp;

@Data
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
}
