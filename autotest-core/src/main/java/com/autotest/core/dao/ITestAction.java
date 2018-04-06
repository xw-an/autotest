package com.autotest.core.dao;

import com.autotest.core.annotation.DataSource;
import com.autotest.core.model.TestAction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


public interface ITestAction {
    @DataSource("Mysql_autotest")
    public void delete(int id);

    @DataSource("Mysql_autotest")
    public void update(TestAction ta);

    @DataSource("Mysql_autotest")
    public void insert(TestAction ta);

    @DataSource("Mysql_autotest")
    public TestAction select(int id);

    @DataSource("Mysql_autotest")
    public List<TestAction> selectList(Map<String, Object> parameters);
}
