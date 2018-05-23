package com.autotest.core.model;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class TestUser {
    private int id;
    private String username;
    private String password;
    private String userId;
    private String role;
    private Timestamp dataChange_CreateTime;
    private Timestamp dataChange_LastTime;
}
