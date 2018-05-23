package com.autotest.core.model;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class TestStep {
    private int id;
    private int case_id;
    private String step_name;
    private String action_type;
    private Timestamp dataChange_CreateTime;
    private Timestamp dataChange_LastTime;

}
