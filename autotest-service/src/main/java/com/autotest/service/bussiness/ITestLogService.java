package com.autotest.service.bussiness;

import com.autotest.core.model.TestLog;
import java.util.List;

public interface ITestLogService {

    public List<TestLog> selectByResultId(int resultId);
    public void insertLog(TestLog tLog);
    public void updateResultIdById(int resultId,List<Integer> logIds);

}
