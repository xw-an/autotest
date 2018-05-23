package com.autotest.core.model;

import lombok.Data;

@Data
public class TestResultCase {

    private int id;
    private int caseId;
    private String userId;
    private String testScenariosName;
    private String testScenariosType;
    private String result;

}
