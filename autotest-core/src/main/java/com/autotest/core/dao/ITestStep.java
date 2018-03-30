package com.autotest.core.dao;

import com.autotest.core.model.TestStep;
import java.util.List;
import java.util.Map;

public interface ITestStep {

    public void delete(int caseId);
    public void update(TestStep ts);
    public void insert(TestStep ts);
    public TestStep select(int id);
    public List<TestStep> selectList(int caseId);
}
