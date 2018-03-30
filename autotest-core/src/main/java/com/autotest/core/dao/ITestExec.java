package com.autotest.core.dao;

import com.autotest.core.model.TestExec;

import java.util.List;
import java.util.Map;

public interface ITestExec {

    public void delete(int caseId);
    public void update(TestExec te);
    public void insert(TestExec te);
    public TestExec select(int id);
    public List<TestExec> selectList(int caseId,int stepId);
}
