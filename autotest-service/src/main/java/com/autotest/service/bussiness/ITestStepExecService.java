package com.autotest.service.bussiness;

import com.autotest.core.model.TestStepExec;
import java.util.List;

public interface ITestStepExecService {

    public boolean deleteStepExec(int caseId);
    public boolean saveStepExec(int caseId, List<TestStepExec> ltStepExec);
    public List<TestStepExec> selectStepExec(int caseId);

}
