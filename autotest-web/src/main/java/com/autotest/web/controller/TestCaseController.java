package com.autotest.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autotest.core.model.SystemParameters;
import com.autotest.core.model.TestCase;
import com.autotest.core.model.TestStepExec;
import com.autotest.service.bussiness.ITestCaseService;
import com.autotest.service.bussiness.ITestStepExecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/TestCaseManage")
public class TestCaseController {

    @Autowired
    private ITestCaseService testCaseService;
    @Autowired
    private ITestStepExecService testStepExecService;

    @RequestMapping
    public String showTestCaseManage(){
        return "TestCaseManage";
    }


    @RequestMapping(value="/TestCases")
    @ResponseBody
    public String showTestCases(){
        List<TestCase> tcases=new ArrayList<>();
        tcases=testCaseService.listCase(null);
        return JSON.toJSONString(tcases);
    }

    @RequestMapping(value="/TestSteps/{caseId}")
    @ResponseBody
    public String showTestSteps(@PathVariable String caseId){
        List<TestStepExec> ltStepExec=testStepExecService.selectStepExec(Integer.parseInt(caseId));
        List<JSONObject> ljsonObject=new ArrayList<>();
        for(TestStepExec tStepExec:ltStepExec){
            JSONObject jo=new JSONObject();
            jo.put("stepId",String.valueOf(tStepExec.getstepId()));
            jo.put("stepName",tStepExec.getStepName());
            jo.put("actionType",tStepExec.getActionType());
            jo.put("actionMap",JSON.toJSONString(tStepExec.getActionMap()));
            ljsonObject.add(jo);
        }
        return JSON.toJSONString(ljsonObject);
    }


    @RequestMapping(value="/addCase")
    @ResponseBody
    public Map<String,String> addTestCase(@RequestBody TestCase testCase){
        Map<String,String> msg=new HashMap<>();
        boolean execResult=testCaseService.addCase(testCase);
        if(execResult) {
            msg.put("msg", "添加成功");
        }else{
            msg.put("msg","添加失败");
        }
        return msg;
    }

    @RequestMapping(value="{caseId}/deleteCase")
    @ResponseBody
    public Map<String,String> deleteTestCase(@PathVariable int caseId){
        Map<String,String> msg=new HashMap<>();
        boolean execResult=testCaseService.deleteCase(caseId);
        if(execResult) {
            msg.put("msg", "删除成功");
        }else{
            msg.put("msg","删除失败");
        }
        return msg;
    }

    @RequestMapping(value="/updateCase")
    @ResponseBody
    public Map<String,String> updateTestCase(@RequestBody TestCase tCase){
        Map<String,String> msg=new HashMap<>();
        boolean execResult=testCaseService.updateCase(tCase);
        if(execResult) {
            msg.put("msg","更新成功");
        }else{
            msg.put("msg","更新失败");
        }
        return msg;
    }


}
