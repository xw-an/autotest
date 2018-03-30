package com.autotest.core.dao;

import com.autotest.core.annotation.DataSource;
import com.autotest.core.model.TestExec;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TestExecDao extends BaseDao implements ITestExec {

    @DataSource("Mysql_autotest")
    @Override
    public void delete(int caseId) {
        super.delete(TestExecDao.class,caseId);
    }

    @DataSource("Mysql_autotest")
    @Override
    public void update(TestExec te) {
        super.update(TestExecDao.class,te);
    }

    @DataSource("Mysql_autotest")
    @Override
    public void insert(TestExec te) {
        super.insert(TestExecDao.class,te);
    }

    @DataSource("Mysql_autotest")
    @Override
    public TestExec select(int id) {
        return (TestExec)super.select(TestExecDao.class,id);
    }

    @DataSource("Mysql_autotest")
    @Override
    public List<TestExec> selectList(int caseId,int stepId) {
        Map<String,Object> params=new HashMap<>();
        params.put("stepId",stepId);
        params.put("caseId",caseId);
        return (List<TestExec>)super.selectList(TestExecDao.class,params);
    }
}
