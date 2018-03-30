package com.autotest.core.dao;

import com.autotest.core.annotation.DataSource;
import com.autotest.core.model.TestCase;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public class TestCaseDao extends BaseDao implements ITestCase {

    @DataSource("Mysql_autotest")
    @Override
    public void delete(int id) {
        super.delete(TestCaseDao.class,id);
    }

    @DataSource("Mysql_autotest")
    @Override
    public void update(TestCase tc) {
        super.update(TestCaseDao.class,tc);
    }

    @DataSource("Mysql_autotest")
    @Override
    public void insert(TestCase tc) {
        super.insert(TestCaseDao.class,tc);
    }

    @DataSource("Mysql_autotest")
    @Override
    public TestCase select(int id) {
        return (TestCase) select(TestCaseDao.class,id);
    }

    @DataSource("Mysql_autotest")
    @Override
    public List<TestCase> selectList(Map<String, Object> parameters) {
        return (List<TestCase>)selectList(TestCaseDao.class,parameters);
    }
}
