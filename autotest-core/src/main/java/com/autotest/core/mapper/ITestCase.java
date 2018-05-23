package com.autotest.core.mapper;

import com.autotest.core.annotation.DataSource;
import com.autotest.core.model.TestCase;

import java.util.List;
import java.util.Map;

public interface ITestCase {
    @DataSource("Mysql_autotest")
    public void delete(int id);

    @DataSource("Mysql_autotest")
    public void update(TestCase tc);

    @DataSource("Mysql_autotest")
    public void insert(TestCase tc);

    @DataSource("Mysql_autotest")
    public TestCase select(int id);

    @DataSource("Mysql_autotest")
    public List<TestCase> selectList(Map<String, Object> parameters);

}
