package com.autotest.service.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autotest.core.model.SystemParameters;
import com.autotest.core.model.TestCase;
import com.autotest.core.model.TestSuit;
import com.autotest.core.util.HttpClient;
import com.autotest.core.util.SystemParametersUtil;
import com.autotest.service.bussiness.ITestActionService;
import com.autotest.service.bussiness.ITestCaseService;
import com.autotest.service.bussiness.ITestStepExecService;
import com.autotest.service.bussiness.ITestSuitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext.xml")
public class TestService {

    @Autowired
    private ITestActionService taservice;
    @Autowired
    private ITestSuitService tSuitService;
    @Autowired
    private ITestStepExecService tStepExecService;
    @Autowired
    private ITestCaseService tCaseService;

    @Test
    public void testActionService(){
        Map dbs=taservice.selectKey("db");
       /* for(String k:dbs){
            System.out.println(k);
        }*/
    }

    @Test
    public void testActiondb(){
        Map<String,Object> maps=new HashMap<>();
        maps.put("id",1);
        SystemParameters.setCommParameters(maps);

        Map<String, Object> dbMaps=new HashMap<>();
        dbMaps.put("conn","jdbc:mysql://localhost:3306/autotest");
        dbMaps.put("username","root");
        dbMaps.put("password","881113");
        dbMaps.put("sql","update test_case set userId=\"HQ001\" where id=${id}");
        //dbMaps.put("sql","selEct * from test_case where id=${id}");
        dbMaps.put("parameterName","caseId,name,type");
        taservice.execDb(dbMaps);
        Map<String,Object> commPs= SystemParameters.getCommParameters();
        for(String s:commPs.keySet()){
            System.out.println(s+"---"+commPs.get(s));
        }
    }

    @Test
    public void testActionCallInterface(){
        Map<String,Object> maps=new HashMap<>();
        maps.put("url","http://192.168.11.135:28080/huaxia-web/user/message-code");
        maps.put("reqData","{\"mobilePhone\":\"18301742421\",\"type\":\"1\",\"idCard\":\"522223198511205522\"}");
        maps.put("reqType","json");
        maps.put("parameterName","callResult");
        taservice.execCallInterface(maps);
        Map<String,Object> commPs= SystemParameters.getCommParameters();
        for(String s:commPs.keySet()){
            System.out.println(s+"---"+commPs.get(s));
        }
    }

    @Test
    public void testActionCheckValue(){
        Map<String,Object> maps=new HashMap<>();
/*        maps.put("type",0);
        maps.put("expectValue","test");
        maps.put("actualValue","test");*/
        /*SystemParametersUtil.addParameters("id","159");
        maps.put("type",1);
        maps.put("expectValue","${id}");
        maps.put("actualValue","158");*/
        SystemParametersUtil.addParameters("respData","{\"mobilePhone\":\"18301742421\",\"type\":\"1\",\"idCard\":\"522223198511205522\"}");
        maps.put("type",2);
        maps.put("expectValue","#json.parseObject(#respData).get(\"mobilePhone\")");
        maps.put("actualValue","1830174242");

        System.out.println(taservice.execCheckValue(maps));
    }

    @Test
    public void testActionSetParameter(){
        SystemParametersUtil.addParameters("id",123);
        Map<String,Object> maps=new HashMap<>();
        maps.put("parameterName","test");
        maps.put("parameterValue","123456");
        taservice.execSetParameter(maps);
        System.out.println(SystemParameters.getCommParameters());
    }


    @Test
    public void testSuitService(){
        TestSuit testSuit=new TestSuit();
        List<Integer> tCaseIds=new ArrayList<>();
        tCaseIds.add(1);
        testSuit.setCaseIds(tCaseIds);
/*        Map<Integer,Boolean> result=tSuitService.runByOrder(testSuit);
        System.out.println(result);
        System.out.println(SystemParameters.getCommParameters());*/
        Map<Integer,Boolean> result=tSuitService.runBySynchronize(testSuit);
        System.out.println(result);
        //System.out.println(SystemParameters.getCommParameters());多线程的全局变量要在运行线程的时候查看。运行完以后是null
    }

    @Test
    public void testSpringEL(){
        /*
        设置全局变量
         */
        /*SystemParametersUtil.addParameters("resp","{\"mobilePhone\":\"18301742421\",\"type\":\"1\",\"idCard\":\"522223198511205522\"}");


        Map<String,Object> syspars= SystemParameters.getCommParameters();
        for(String k:syspars.keySet()){
            context.setVariable(k,syspars.get(k));
        }*/

        EvaluationContext context = new StandardEvaluationContext();
        String res="{\"mobilePhone\":\"18301742421\",\"type\":\"1\",\"idCard\":\"522223198511205522\"}";
        JSONObject resJson= JSONObject.parseObject(res);
        context.setVariable("res",res);
        context.setVariable("json",com.alibaba.fastjson.JSON.class);
        context.setVariable("resJson",resJson);
        // 1. 构建解析器
        ExpressionParser parse = new SpelExpressionParser();
        // 2. 解析表达式
        Expression exp = parse.parseExpression("#json.parseObject(#res).get(\"mobilePhone\")");
        Expression exp2 = parse.parseExpression("#resJson.get(\"idCard\")");
        // 3. 获取结果
        System.out.println(exp2.getValue(context));
    }

    @Test
    public void testSpringEL02(){
        Map<String,Object> maps=new HashMap<>();
        maps.put("url","http://192.168.11.135:28080/huaxia-web/user/message-code");
        maps.put("reqData","{\"mobilePhone\":\"18301742421\",\"type\":\"1\",\"idCard\":\"522223198511205522\"}");
        maps.put("reqHeader",new HashMap<>());
        maps.put("parameterName","respResult");
        //System.out.println(HttpClient.getResponseByJson(maps));
        String respData=HttpClient.getResponseByJson(maps);

        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("json",com.alibaba.fastjson.JSON.class);
        context.setVariable("res",respData);
        // 1. 构建解析器
        ExpressionParser parse = new SpelExpressionParser();
        // 2. 解析表达式
        Expression exp = parse.parseExpression("#json.parseObject(#res).get(\"responseMsg\")");
        //Expression exp2 = parse.parseExpression("#resJson.get(\"idCard\")");
        // 3. 获取结果
        System.out.println(exp.getValue(context));
    }

    @Test
    public void testStepService(){
        //System.out.println(tStepExecService.selectStepExec(1));
        List<JSONObject> l=new ArrayList<>();
        JSONObject jsonObject1=new JSONObject();
        jsonObject1.put("id","1");
        jsonObject1.put("name","张三");
        JSONObject jsonObject2=new JSONObject();
        jsonObject2.put("id","2");
        jsonObject2.put("name","里斯");
        l.add(jsonObject1);
        l.add(jsonObject2);
        System.out.println(JSON.toJSONString(l));
    }

    @Test
    public void testCaseService(){
/*        TestCase tCase=new TestCase();
        tCase.setTestScenariosName("123");
        tCase.setTestScenariosType("123");
        tCase.setTestContent("123");
        tCase.setRemark("123");
        tCase.setUserId("HQ");
        tCaseService.addCase(tCase);*/
        System.out.println(tCaseService.runTestCase(1));
    }

    @Test
    public void testJson(){
        String str1="[{\"caseId\":1,}]";
    }
}
