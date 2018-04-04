package com.autotest.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.autotest.core.model.TestStepExec;
import com.autotest.service.bussiness.ITestActionService;
import com.autotest.service.bussiness.ITestStepExecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Autowired
    private ITestActionService testActionService;

    @RequestMapping
    public String showTestStepManage(){
        return "TestStepManage";
    }

    /**
     * 查询用例id对应的所有步骤
     * @param searchParams 查询筛选条件
     * @return
     */
    @RequestMapping("/caseSteps")
    @ResponseBody
    public String showCaseSteps(@RequestBody JSONObject searchParams){
        if(searchParams.getString("caseId")==null) return null;
        int caseId=Integer.parseInt(searchParams.getString("caseId"));
        List<TestStepExec> ltStepExec=testStepExecService.selectStepExec(caseId);
        if(ltStepExec==null) return null;
        List<JSONObject> lsteps=new ArrayList<>();
        for(TestStepExec tStepExec:ltStepExec){
            JSONObject jo=new JSONObject();
            jo.put("stepId",String.valueOf(tStepExec.getstepId()));
            jo.put("stepName",tStepExec.getStepName());
            jo.put("actionType",tStepExec.getActionType());
            jo.put("actionMap", JSON.toJSONString(tStepExec.getActionMap()));
            lsteps.add(jo);
        }
        return JSON.toJSONString(lsteps);
    }

    /**
     * 获取操作类型对应的操作参数集，做界面展示
     * @return
     */
    @RequestMapping("{actionType}/getActionParams")
    @ResponseBody
    public String getActionMap(@PathVariable String actionType){
        List<String> actionParams=testActionService.selectKey(actionType);
        return JSON.toJSONString(actionParams);
    }
}
