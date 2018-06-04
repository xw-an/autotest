package com.autotest.core.mapper;

import com.autotest.core.annotation.DataSource;
import com.autotest.core.model.TestAction;

import java.util.List;
import java.util.Map;

@DataSource("autotest")
public interface ITestAction {

    public void delete(int id);
    public void update(TestAction ta);
    public void insert(TestAction ta);
    public TestAction select(int id);
    public List<TestAction> selectList(Map<String, Object> parameters);
}
