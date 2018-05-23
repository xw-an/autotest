package com.autotest.core.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TestAction {
    private int id;
    private String actionType;
    private String keyName;
    private String name;
    private Timestamp dataChange_CreateTime;
    private Timestamp dataChange_LastTime;
}
