package com.autotest.core.model;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class TestResult {

    private int id;
    private int case_id;
    private String result;
    private String userId;
    private Timestamp dataChange_CreateTime;
    private Timestamp dataChange_LastTime;
}
