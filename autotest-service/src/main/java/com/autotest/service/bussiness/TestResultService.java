package com.autotest.service.bussiness;

import com.autotest.core.mapper.ITestResult;
import com.autotest.core.model.TestResult;
import com.autotest.core.model.TestResultCase;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Log4j
@Service
public class TestResultService implements ITestResultService{
    @Autowired
    private ITestResult tResultDao;

    @Override
    public List<TestResultCase> listResult(Map<String, Object> params) {
        return tResultDao.list(params);
    }

    @Override
    public void insert(TestResult tResult) {
        tResultDao.insert(tResult);
    }

    @Override
    public void update(TestResult tResult) {
        tResultDao.update(tResult);
    }
}
