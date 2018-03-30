package com.autotest.core.dao;

import com.autotest.core.annotation.DataSource;
import com.autotest.core.model.TestStep;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TestStepDao extends BaseDao implements ITestStep{
    @DataSource("Mysql_autotest")
    @Override
    public void delete(int caseId) {
        super.delete(TestStepDao.class,caseId);
    }

    @DataSource("Mysql_autotest")
    @Override
    public void update(TestStep ts) {
        super.update(TestStepDao.class,ts);
    }

    @DataSource("Mysql_autotest")
    @Override
    public void insert(TestStep ts) {
        super.insert(TestStepDao.class,ts);
    }

    @DataSource("Mysql_autotest")
    @Override
    public TestStep select(int id) {
        return (TestStep)super.select(TestStepDao.class,id);
    }

    @DataSource("Mysql_autotest")
    @Override
    public List<TestStep> selectList(int caseId) {
        Map<String,Object> params=new HashMap<>();
        params.put("caseId",caseId);
        return (List<TestStep>) super.selectList(TestStepDao.class,params);
    }
}
