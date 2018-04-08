package com.autotest.service.bussiness;

import com.autotest.core.model.TestResult;
import com.autotest.core.model.TestResultCase;

import java.util.List;
import java.util.Map;

public interface ITestResultService {

    public List<TestResultCase> listResult(Map<String,Object> params);

    public void insert(TestResult tResult);

    public void update(TestResult tResult);

}
