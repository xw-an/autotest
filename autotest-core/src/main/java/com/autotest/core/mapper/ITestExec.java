package com.autotest.core.mapper;

import com.autotest.core.annotation.DataSource;
import com.autotest.core.model.TestExec;

import java.util.List;
import java.util.Map;

@DataSource("autotest")
public interface ITestExec {
    public void delete(int caseId);
    public void update(TestExec te);
    public void insert(TestExec te);
    public TestExec select(int id);
    public List<TestExec> selectList(Map<String, Object> parameters);
}
