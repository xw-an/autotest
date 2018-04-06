package com.autotest.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autotest.core.model.TestResult;
import com.autotest.core.model.TestResultCase;
import com.autotest.service.bussiness.ITestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/LogList",method = RequestMethod.GET)
    @ResponseBody
    public String showLogList(){
        return null;
    }

    @RequestMapping(value = "/LogList",method = RequestMethod.POST)
    @ResponseBody
    public String showLogList(@RequestBody JSONObject logParams){
        Map<String,Object> params=new HashMap<>();
        String caseId=logParams.getString("runCaseId");
        if(caseId!=null&&!caseId.equals("")){
            params.put("caseId",caseId);
        }
        String userId=logParams.getString("runUserId");
        if(!userId.equals("All")){
            params.put("userId",userId);
        }
        params.put("runStartTime",logParams.getString("runStartTime"));
        params.put("runEndTime",logParams.getString("runEndTime"));
        params.put("caseName",logParams.getString("runCaseName"));
        params.put("caseType",logParams.getString("runCaseType"));
        String result=logParams.getString("runCaseResult");
        if(!result.equals("All")){
            params.put("result",result);
        }
        List<TestResultCase> testResultList=testResultService.listResult(params);
        return JSON.toJSONString(testResultList);
    }

    @RequestMapping("/LogDetail")
    @ResponseBody
    public String showLogDetail(@PathVariable String execId){
        return null;
    }
}
