package com.autotest.core.dao;

import com.autotest.core.model.TestCase;

import java.util.List;
import java.util.Map;

public interface ITestCase {
    public void delete(int id);
    public void update(TestCase tc);
    public void insert(TestCase tc);
    public TestCase select(int id);
    public List<TestCase> selectList(Map<String,Object> parameters);

}
