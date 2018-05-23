package com.autotest.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autotest.core.model.TestCase;
import com.autotest.core.model.TestStepExec;
import com.autotest.service.bussiness.ITestActionService;
import com.autotest.service.bussiness.ITestCaseService;
import com.autotest.service.bussiness.ITestStepExecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/TestStepManage")
public class TestStepController {
    @Autowired
    private ITestStepExecService testStepExecService;
    @Autowired
    private ITestActionService testActionService;
    @Autowired
    private ITestCaseService testCaseService;

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
        if(!searchParams.containsKey("caseId")) return null;
        JSONObject searchResult=new JSONObject();
        int caseId=Integer.parseInt(searchParams.getString("caseId"));
        try {
            testCaseService.selectCase(caseId);
            List<TestStepExec> ltStepExec=testStepExecService.selectStepExec(caseId);
            if(ltStepExec==null){
                searchResult.put("result",true);
                searchResult.put("content",null);
                return JSONObject.toJSONString(searchResult);
            }else{
                List<JSONObject> lsteps=new ArrayList<>();
                for(TestStepExec tStepExec:ltStepExec){
                    JSONObject jo=new JSONObject();
                    jo.put("stepId",String.valueOf(tStepExec.getStepId()));
                    jo.put("stepName",tStepExec.getStepName());
                    jo.put("actionType",tStepExec.getActionType());
                    jo.put("actionMap", JSON.toJSONString(tStepExec.getActionMap()));
                    lsteps.add(jo);
                }
                searchResult.put("result",true);
                searchResult.put("content",JSON.toJSONString(lsteps));
                return JSONObject.toJSONString(searchResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
            searchResult.put("result",false);
            searchResult.put("message","当前用例id:"+caseId+"不存在");
            return JSONObject.toJSONString(searchResult);
        }
    }

    /**
     * 获取操作类型对应的操作参数集，做界面展示
     * @return
     */
    @RequestMapping("{actionType}/getActionParams")
    @ResponseBody
    public String getActionMap(@PathVariable String actionType){
       Map<String,String> actionParams=testActionService.selectKey(actionType);
       return JSON.toJSONString(actionParams);
    }

    /**
     * 保存当前用例的所有步骤
     * @param caseId
     * @param steps
     * @return
     */
    @RequestMapping("{caseId}/SaveSteps")
    @ResponseBody
    public String saveSteps(@PathVariable int caseId,@RequestBody List<JSONObject> steps){
        JSONObject jsonResult=new JSONObject();
        TestCase testCase=testCaseService.selectCase(caseId);
        if(testCase==null||testCase.equals("")) {
            //用例id不存在，不做插入的功能
            jsonResult.put("result",false);
            jsonResult.put("msg","当前测试用例id不存在");
           return JSONObject.toJSONString(jsonResult);
        }else{
            List<TestStepExec> ltestStepExec=new ArrayList<>();
            for(JSONObject s:steps){
                TestStepExec testStepExec=new TestStepExec();
                //testStepExec.setstepId(Integer.parseInt(s.getString("stepId")));
                testStepExec.setStepName(s.getString("stepName"));
                testStepExec.setActionType(s.getString("actionType"));
                String actionMap=s.getString("actionMap");
                testStepExec.setActionMap(JSON.parseObject(actionMap,Map.class));
                ltestStepExec.add(testStepExec);
            }
            boolean result=testStepExecService.saveStepExec(caseId,ltestStepExec);
            jsonResult.put("result",result);
            return JSONObject.toJSONString(jsonResult);
        }
    }
}
