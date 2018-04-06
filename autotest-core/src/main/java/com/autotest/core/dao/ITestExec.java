package com.autotest.core.dao;

import com.autotest.core.annotation.DataSource;
import com.autotest.core.model.TestExec;

import java.util.List;
import java.util.Map;

public interface ITestExec {
    @DataSource("Mysql_autotest")
    public void delete(int caseId);

    @DataSource("Mysql_autotest")
    public void update(TestExec te);

    @DataSource("Mysql_autotest")
    public void insert(TestExec te);

    @DataSource("Mysql_autotest")
    public TestExec select(int id);

    @DataSource("Mysql_autotest")
    public List<TestExec> selectList(Map<String, Object> parameters);
}
