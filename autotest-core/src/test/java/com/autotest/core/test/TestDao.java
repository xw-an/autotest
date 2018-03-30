package com.autotest.core.test;

import com.autotest.core.dao.ITestAction;
import com.autotest.core.dao.ITestCase;
import com.autotest.core.dao.ITestExec;
import com.autotest.core.dao.ITestStep;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class TestDao {

    @Autowired
    private ITestCase tcdao;
    @Autowired
    private ITestStep tsdao;
    @Autowired
    private ITestAction tadao;
    @Autowired
    private ITestExec tedao;

    @Test
    public void testcase(){
        System.out.println(tcdao.select(1));
    }

    @Test
    public void teststep(){
        System.out.println(tsdao.select(1));
    }

    @Test
    public void testaction(){
        System.out.println(tadao.select(1));
    }

    @Test
    public void testexec(){
        System.out.println(tedao.select(1));
    }


}
