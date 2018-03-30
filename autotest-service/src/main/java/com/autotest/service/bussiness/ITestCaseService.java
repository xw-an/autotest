package com.autotest.service.bussiness;

import com.autotest.core.model.TestCase;
import java.util.List;
import java.util.Map;

public interface ITestCaseService {

    public boolean addCase(TestCase tcase);
    public boolean updateCase(TestCase tcase);
    public boolean deleteCase(int caseId);
    public TestCase selectCase(int caseId);
    public List<TestCase> listCase(Map<String,Object> filterMaps);
    public boolean runTestCase(int caseId);


}
