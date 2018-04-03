package com.autotest.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autotest.core.model.TestStepExec;
import com.autotest.service.bussiness.ITestStepExecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/TestStepManage")
public class TestStepController {
    @Autowired
    private ITestStepExecService testStepExecService;

    @RequestMapping
    public String showTestStepManage(){
        return "TestStepManage";
    }

    @RequestMapping(value = "/caseSteps")
    @ResponseBody
    public String showCaseSteps(@RequestBody JSONObject searchParams){
        if(searchParams.getString("caseId")==null) return null;
        int caseId=Integer.parseInt(searchParams.getString("caseId"));
        List<TestStepExec> ltStepExec=testStepExecService.selectStepExec(caseId);
        List<JSONObject> ljsonObject=new ArrayList<>();
        for(TestStepExec tStepExec:ltStepExec){
            JSONObject jo=new JSONObject();
            jo.put("stepId",String.valueOf(tStepExec.getstepId()));
            jo.put("stepName",tStepExec.getStepName());
            jo.put("actionType",tStepExec.getActionType());
            jo.put("actionMap", JSON.toJSONString(tStepExec.getActionMap()));
            ljsonObject.add(jo);
        }
        return JSON.toJSONString(ljsonObject);
    }
}
