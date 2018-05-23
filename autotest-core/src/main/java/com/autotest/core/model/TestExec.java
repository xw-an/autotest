package com.autotest.core.model;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class TestExec {
    private int id;
    private int case_id;
    private int step_id;
    private String action_type;
    private String action_key;
    private String action_value;
    private Timestamp dataChange_CreateTime;
    private Timestamp dataChange_LastTime;
}
