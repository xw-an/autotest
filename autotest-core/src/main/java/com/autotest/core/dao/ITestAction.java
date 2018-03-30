package com.autotest.core.dao;

import com.autotest.core.model.TestAction;
import java.util.List;
import java.util.Map;

public interface ITestAction {
    public void delete(int id);
    public void update(TestAction ta);
    public void insert(TestAction ta);
    public TestAction select(int id);
    public List<TestAction> selectList(Map<String,Object> parameters);
}
