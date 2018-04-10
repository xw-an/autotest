package com.autotest.service.bussiness;

import com.alibaba.fastjson.JSON;
import com.autotest.core.dao.ITestCase;
import com.autotest.core.dao.ITestExec;
import com.autotest.core.dao.ITestStep;
import com.autotest.core.model.FailException;
import com.autotest.core.model.TestCase;
import com.autotest.core.model.TestResult;
import com.autotest.core.model.TestStepExec;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private ITestResultService tResultService;
    private Logger logger= Logger.getLogger(this.getClass());

    @Override
    @Transactional
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
    @Transactional
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
    @Transactional
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
    public boolean runTestCase(int caseId,String userId) {
        //执行测试用例前，先插入执行结果表状态为"Run"
        TestResult tResult=new TestResult();
        tResult.setCase_id(caseId);
        tResult.setResult("Run");
        if(userId==null||userId.equals("")){
            userId="test";
        }
        tResult.setUserId(userId);
        tResultService.insert(tResult);
        int resultId=tResult.getId();
        //根据resultId开始记录日志
        MDC.put("resultId",String.valueOf(resultId));
        logger.info("-----[用例id:"+caseId+"]:开始运行用例-----");

        boolean resultStatus=false;
        List<TestStepExec> ltStepExec=tStepExecService.selectStepExec(caseId);
        if(ltStepExec==null){
            logger.info("-----[用例id:"+caseId+"]:当前用例无可执行步骤-----");
            resultStatus=true;
        }
        else{
            for(TestStepExec tStepExec:ltStepExec){
                int stepId=tStepExec.getstepId();
                String stepName=tStepExec.getStepName();
                logger.info("-----[用例id:"+caseId+"]:开始执行步骤:"+stepName+"-----");
                String actionType=tStepExec.getActionType();
                String methodName="exec"+actionType.substring(0,1).toUpperCase()+actionType.substring(1);
                logger.info("[当前步骤:"+stepId+"]:需要调用的方法名"+methodName);
                try {
                /*
                根据类型名称反射调用对应类型的方法执行
                 */
                    Method m=tActionService.getClass().getMethod(methodName,Map.class);
                    resultStatus=(boolean) m.invoke(tActionService,tStepExec.getActionMap());
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    logger.error("[当前步骤:"+stepId+"]:执行异常"+e.getMessage());
                    resultStatus=false;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    logger.error("[当前步骤:"+stepId+"]:执行异常"+e.getMessage());
                    resultStatus=false;
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                    logger.error("[当前步骤:"+stepId+"]:执行异常"+e.getMessage());
                    resultStatus=false;
                }
                logger.info("-----[当前步骤:"+stepId+"]:执行结果"+resultStatus+"-----");
                if(!resultStatus) break;
            }
        }

        //插入运行结果表
        if(resultStatus){
            tResult.setResult("Success");
        }else{
            tResult.setResult("Fail");
        }
        tResultService.update(tResult);
        logger.info("-----[用例id:"+caseId+"]:用例运行结束-----");
        return resultStatus;
    }
}
