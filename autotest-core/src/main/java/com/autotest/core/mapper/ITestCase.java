package com.autotest.core.mapper;

import com.autotest.core.annotation.DataSource;
import com.autotest.core.model.TestCase;

import java.util.List;
import java.util.Map;

@DataSource("autotest")
public interface ITestCase {
    public void delete(int id);
    public void update(TestCase tc);
    public void insert(TestCase tc);
    public TestCase select(int id);
    public List<TestCase> selectList(Map<String, Object> parameters);

}
