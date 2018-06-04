package com.autotest.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.autotest.core.model.TestLog;
import com.autotest.core.model.TestResult;
import com.autotest.core.model.TestResultCase;
import com.autotest.service.bussiness.ITestLogService;
import com.autotest.service.bussiness.ITestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/TestLogManage")
public class TestLogController {
    @Autowired
    private ITestResultService testResultService;
    @Autowired
    private ITestLogService testLogService;

    /**
     * 页面跳转到TestLogManage.jsp
     * @return
     */
    @RequestMapping
    public String showTestLog(){
        return "TestLogManage";
    }

    /**
     * 第一次表格get请求
     * @return
     */
    @RequestMapping(value = "/LogList",method = RequestMethod.GET)
    @ResponseBody
    public String showLogList(){
        return null;
    }

    /**
     * 根据筛选条件post获取表格数据
     * @param logParams
     * @return
     */
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

    /**
     * 根据父表格的resultId获取子表的数据
     * @param resultId
     * @return
     */
    @RequestMapping("/LogDetail/{resultId}")
    @ResponseBody
    public String showLogDetail(@PathVariable String resultId){
        List<TestLog> ltestLog=testLogService.selectByResultId(Integer.parseInt(resultId));
        return JSON.toJSONString(ltestLog,SerializerFeature.WriteDateUseDateFormat);
    }
}
