package com.autotest.core.dao;

import com.autotest.core.annotation.DataSource;
import com.autotest.core.model.TestAction;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public class TestActionDao extends BaseDao implements ITestAction {

    @DataSource("Mysql_autotest")
    @Override
    public void delete(int id) {
        super.delete(TestActionDao.class,id);
    }

    @DataSource("Mysql_autotest")
    @Override
    public void update(TestAction ta) {
        super.update(TestActionDao.class,ta);
    }

    @DataSource("Mysql_autotest")
    @Override
    public void insert(TestAction ta) {
        super.insert(TestActionDao.class,ta);
    }

    @DataSource("Mysql_autotest")
    @Override
    public TestAction select(int id) {
        return (TestAction)super.select(TestActionDao.class,id);
    }

    @DataSource("Mysql_autotest")
    @Override
    public List<TestAction> selectList(Map<String, Object> parameters) {
        return (List<TestAction>)super.selectList(TestActionDao.class,parameters);
    }
}
