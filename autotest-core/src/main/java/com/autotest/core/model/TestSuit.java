package com.autotest.core.model;

import lombok.Data;
import java.util.List;

@Data
public class TestSuit {
    List<Integer> caseIds;
    String userId;
}
