package com.autotest.core.dao;

import com.autotest.core.annotation.DataSource;
import com.autotest.core.model.TestStep;

import java.util.List;
import java.util.Map;

public interface ITestStep {

    @DataSource("Mysql_autotest")
    public void delete(int caseId);

    @DataSource("Mysql_autotest")
    public void update(TestStep ts);

    @DataSource("Mysql_autotest")
    public void insert(TestStep ts);

    @DataSource("Mysql_autotest")
    public TestStep select(int id);

    @DataSource("Mysql_autotest")
    public List<TestStep> selectList(int caseId);
}
