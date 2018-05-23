package com.autotest.core.test;

import com.autotest.core.mapper.ITestResult;
import com.autotest.core.util.HttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class TestUtil {
    @Autowired
    private ITestResult testResult;

    @Test
    public void testHttpClient(){
        Map<String,Object> maps=new HashMap<>();
        maps.put("url","http://192.168.11.135:28080/huaxia-web/user/message-code");
        maps.put("reqData","{\"mobilePhone\":\"18301742421\",\"type\":\"1\",\"idCard\":\"522223198511205522\"}");
        maps.put("reqHeader",new HashMap<>());
        maps.put("parameterName","respResult");
        System.out.println(HttpClient.getResponseByJson(maps));
    }

    @Test
    public void testResult(){
        int caseId=1;
        testResult.select(caseId);
    }
}
