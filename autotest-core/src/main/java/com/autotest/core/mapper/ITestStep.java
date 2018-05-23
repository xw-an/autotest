package com.autotest.core.mapper;

import com.autotest.core.annotation.DataSource;
import com.autotest.core.model.TestStep;

import java.util.List;

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
