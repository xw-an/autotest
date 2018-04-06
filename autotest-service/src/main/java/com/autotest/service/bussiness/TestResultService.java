package com.autotest.service.bussiness;

import com.autotest.core.dao.ITestResult;
import com.autotest.core.model.TestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class TestResultService implements ITestResultService{
    @Autowired
    private ITestResult tResultDao;

    @Override
    public List<TestResult> listResult(Map<String, Object> params) {
        return tResultDao.list(params);
    }

    @Override
    public void insert(TestResult tResult) {
        tResultDao.insert(tResult);
    }
}
