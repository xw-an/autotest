package com.autotest.service.bussiness;

import com.autotest.core.dao.ITestCase;
import com.autotest.core.dao.ITestExec;
import com.autotest.core.dao.ITestStep;
import com.autotest.core.model.FailException;
import com.autotest.core.model.TestCase;
import com.autotest.core.model.TestStepExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Service
public class TestCaseService implements ITestCaseService {

    @Autowired
    private ITestCase tcDao;
    @Autowired
    private ITestStep tsDao;
    @Autowired
    private ITestExec teDao;
    @Autowired
    private ITestStepExecService tStepExecService;
    @Autowired
    private ITestActionService tActionService;

    @Override
    public boolean addCase(TestCase tcase) {
        try {
            tcDao.insert(tcase);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateCase(TestCase tcase) {
        TestCase tc=tcDao.select(tcase.getId());
        if(tc==null||tc.equals("")) throw new FailException("要更新的用例不存在");
        try {
            tcDao.update(tcase);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteCase(int caseId) {
        TestCase tc=tcDao.select(caseId);
        if(tc==null||tc.equals("")) throw new FailException("要删除的用例不存在");
        try {
            /*
            删除用例的同时删除步骤和执行内容表中的数据
             */
            teDao.delete(caseId);
            tsDao.delete(caseId);
            tcDao.delete(caseId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public TestCase selectCase(int caseId) {
        TestCase tc=tcDao.select(caseId);
        if(tc==null||tc.equals("")) throw new FailException("要查询的用例不存在");
        return tc;
    }

    @Override
    public List<TestCase> listCase(Map<String, Object> filterMaps) {
        List<TestCase> lcases=tcDao.selectList(filterMaps);
        return lcases;
    }

    /**
     * 运行单条测试用例
     * @param caseId 测试用例
     * @return 返回运行结果
     */
    @Override
    public boolean runTestCase(int caseId) {
        boolean resultStatus=false;
        List<TestStepExec> ltStepExec=tStepExecService.selectStepExec(caseId);
        for(TestStepExec tStepExec:ltStepExec){
            String actionType=tStepExec.getActionType();
            String methodName="exec"+actionType.substring(0,1).toUpperCase()+actionType.substring(1);
            try {
                /*
                根据类型名称反射调用对应类型的方法执行
                 */
                Method m=tActionService.getClass().getMethod(methodName,Map.class);
                resultStatus=(boolean) m.invoke(tActionService,tStepExec.getActionMap());
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                resultStatus=false;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                resultStatus=false;
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                resultStatus=false;
            }finally {
                if(!resultStatus) return false;
            }
        }
        return resultStatus;
    }
}
