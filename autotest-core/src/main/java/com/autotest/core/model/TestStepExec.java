package com.autotest.core.model;

import lombok.Data;
import java.util.Map;

@Data
public class TestStepExec {
    private int stepId;
    private String stepName;
    private String actionType;
    private Map<String,String> actionMap;

}
