package com.autotest.core.dao;

import com.autotest.core.model.TestResult;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ITestResult {

    public TestResult select(int caseId);
    public void delete();
    public void update(TestResult tResult);
    public void insert(TestResult tResult);

    public List<TestResult> list(Map<String,Object> params);
}
