package com.autotest.core.model;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class TestCase {
    private int id;
    private String testScenariosName;
    private String testScenariosType;
    private String testContent;
    private String remark;
    private String userId;
    private Timestamp dataChange_CreateTime;
    private Timestamp dataChange_LastTime;
}
