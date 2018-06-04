package com.autotest.core.mapper;

import com.autotest.core.annotation.DataSource;
import com.autotest.core.model.TestStep;

import java.util.List;

@DataSource("autotest")
public interface ITestStep {

    public void delete(int caseId);
    public void update(TestStep ts);
    public void insert(TestStep ts);
    public TestStep select(int id);
    public List<TestStep> selectList(int caseId);
}
