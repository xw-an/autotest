package com.autotest.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autotest.core.model.TestResult;
import com.autotest.service.bussiness.ITestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/TestLogManage")
public class TestLogController {
    @Autowired
    private ITestResultService testResultService;

    @RequestMapping
    public String showTestLog(){
        return "TestLogManage";
    }

    @RequestMapping("/LogList")
    @ResponseBody
    public String showLogList(@RequestBody JSONObject logParams){
        Map<String,Object> params=new HashMap<>();
        params.put("caseId",Integer.parseInt(logParams.getString("runCaseId")));
        params.put("runStartTime",logParams.getString("runStartTime"));
        params.put("runEndTime",logParams.getString("runEndTime"));
        params.put("caseName",logParams.getString("runCaseName"));
        params.put("caseType",logParams.getString("runCaseType"));
        params.put("result",logParams.getString("runCaseResult"));
        List<TestResult> testResultList=testResultService.listResult(params);
        return JSON.toJSONString(testResultList);
    }

    @RequestMapping("/LogDetail")
    @ResponseBody
    public String showLogDetail(@PathVariable String execId){
        return null;
    }
}
