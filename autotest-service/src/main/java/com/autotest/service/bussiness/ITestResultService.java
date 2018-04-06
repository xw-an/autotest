package com.autotest.service.bussiness;

import com.autotest.core.model.TestResult;
import java.util.List;
import java.util.Map;

public interface ITestResultService {

    public List<TestResult> listResult(Map<String,Object> params);

    public void insert(TestResult tResult);

}
