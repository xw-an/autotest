package com.autotest.core.mapper;

import com.autotest.core.annotation.DataSource;
import com.autotest.core.model.TestResult;
import com.autotest.core.model.TestResultCase;

import java.util.List;
import java.util.Map;

public interface ITestResult {
    @DataSource("Mysql_autotest")
    public TestResult select(int caseId);

    @DataSource("Mysql_autotest")
    public void delete(int id);

    @DataSource("Mysql_autotest")
    public void update(TestResult tResult);

    @DataSource("Mysql_autotest")
    public void insert(TestResult tResult);

    @DataSource("Mysql_autotest")
    public List<TestResultCase> list(Map<String, Object> params);
}
