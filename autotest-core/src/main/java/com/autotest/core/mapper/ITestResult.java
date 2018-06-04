package com.autotest.core.mapper;

import com.autotest.core.annotation.DataSource;
import com.autotest.core.model.TestResult;
import com.autotest.core.model.TestResultCase;

import java.util.List;
import java.util.Map;

@DataSource("autotest")
public interface ITestResult {
    public TestResult select(int caseId);
    public void delete(int id);
    public void update(TestResult tResult);
    public void insert(TestResult tResult);
    public List<TestResultCase> list(Map<String, Object> params);
}
