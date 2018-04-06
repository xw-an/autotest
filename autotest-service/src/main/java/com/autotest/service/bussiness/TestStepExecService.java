package com.autotest.service.bussiness;

import com.autotest.core.dao.ITestExec;
import com.autotest.core.dao.ITestStep;
import com.autotest.core.model.TestExec;
import com.autotest.core.model.TestStep;
import com.autotest.core.model.TestStepExec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestStepExecService implements ITestStepExecService {
    @Autowired
    private ITestStep tsDao;
    @Autowired
    private ITestExec teDao;


    /**
     * 删除测试用例下的所有步骤和步骤内容
     * @param caseId 测试用例id
     * @return 返回执行结果
     */
    @Override
    @Transactional
    public boolean deleteStepExec(int caseId) {
        try {
            tsDao.delete(caseId);
            teDao.delete(caseId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 保存操作步骤和操作内容
     * @param caseId 测试用例id
     * @param ltStepExec 要保存的所有步骤和步骤内容
     * @return 返回执行结果
     */
    @Override
    @Transactional
    public boolean saveStepExec(int caseId, List<TestStepExec> ltStepExec) {
        try {
            /*
            先删除测试用例下所有步骤和步骤操作内容
             */
            boolean isDelete=deleteStepExec(caseId);
            if(isDelete){
                      /*
            插入步骤和执行操作
             */
                for(TestStepExec tStepExec : ltStepExec){
                    TestStep tStep=new TestStep();
                    tStep.setCase_id(caseId);
                    tStep.setStep_name(tStepExec.getStepName());
                    tStep.setAction_type(tStepExec.getActionType());
                    tsDao.insert(tStep);//插入步骤
                    int step_id=tStep.getId();
                    TestExec tExec=new TestExec();
                    tExec.setCase_id(caseId);
                    tExec.setStep_id(step_id);
                    tExec.setAction_type(tStepExec.getActionType());
                    Map<String,String> actionMap=tStepExec.getActionMap();
                    for(String k:actionMap.keySet()){
                        tExec.setAction_key(k);
                        tExec.setAction_value(actionMap.get(k));
                        teDao.insert(tExec);//插入执行内容
                    }
                }
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 根据测试用例id，查询返回的步骤集合
     * @param caseId 测试用例id
     * @return 返回步骤和步骤类型的具体执行参数
     */
    @Override
    public List<TestStepExec> selectStepExec(int caseId) {
        List<TestStepExec> ltStepExec=new ArrayList<>();
        List<TestStep> ltStep=tsDao.selectList(caseId);
        if(ltStep.size()<1) return null;
        for(TestStep ts:ltStep){
            Map<String,String> actionMaps=new HashMap<>();
            List<TestExec> ltExec= teDao.selectList(caseId,ts.getId());//根据操作类型查询对应key,value
            for(TestExec te:ltExec){
                actionMaps.put(te.getAction_key(),te.getAction_value());
            }

            TestStepExec tStepExec=new TestStepExec();
            tStepExec.setstepId(ts.getId());
            tStepExec.setActionType(ts.getAction_type());
            tStepExec.setStepName(ts.getStep_name());
            tStepExec.setActionMap(actionMaps);//添加当前步骤操作类型的key,value集合
            ltStepExec.add(tStepExec);//
        }
        return ltStepExec;
    }
}
