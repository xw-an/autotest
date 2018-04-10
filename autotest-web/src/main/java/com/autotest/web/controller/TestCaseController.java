package com.autotest.web.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.autotest.core.model.TestCase;
import com.autotest.core.model.TestStepExec;
import com.autotest.core.model.TestSuit;
import com.autotest.core.model.TestUser;
import com.autotest.service.bussiness.ITestCaseService;
import com.autotest.service.bussiness.ITestStepExecService;
import com.autotest.service.bussiness.ITestSuitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alibaba.fastjson.JSON.parseArray;

@Controller
@RequestMapping("/TestCaseManage")
public class TestCaseController {

    @Autowired
    private ITestCaseService testCaseService;
    @Autowired
    private ITestStepExecService testStepExecService;
    @Autowired
    private ITestSuitService testSuitService;
    @Autowired
    private HttpServletRequest request;

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
        if(ltStepExec==null||ltStepExec.size()==0) return null;
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
        HttpSession session=request.getSession();
        TestUser testUser=(TestUser)session.getAttribute("loginUser");
        testCase.setUserId(testUser.getUserId());//获取session中登陆用户
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

    @RequestMapping("/runCase")
    @ResponseBody
    public String runCase(@RequestBody JSONObject runParams){
        JSONArray jsonArray=new JSONArray();
        Map<Integer,Boolean> execResult=new HashMap<>();
        Map<Integer,Integer> rowToId=new HashMap<>();//定义行号和用例id对应关系
        List<Integer> lcaseIds=new ArrayList<>();//定义用例id集合
        int type=runParams.getInteger("type");//获取运行类型
        JSONArray jArray=runParams.getJSONArray("caseIds");//获取运行用例的集合
        //映射行号和用例id之间的关系
        for (Object obj : jArray) {
            JSONObject c = (JSONObject) obj;
            int rowId=c.getInteger("rowId");
            int caseId=c.getInteger("caseId");
            rowToId.put(caseId,rowId);
            lcaseIds.add(caseId);
        }
        TestSuit testSuit=new TestSuit();
        //获取session
        HttpSession session=request.getSession();
        TestUser loginUser=(TestUser) session.getAttribute("loginUser");//获取登陆用户的userId
        testSuit.setUserId(loginUser.getUserId());
        testSuit.setCaseIds(lcaseIds);
        switch(type){
            case 1://单线程运行
                execResult=testSuitService.runByOrder(testSuit);
                break;
            case 2://多线程运行
                execResult=testSuitService.runBySynchronize(testSuit);
                break;
            default:break;
        }
        for(Integer id:execResult.keySet()){
            int rowId=rowToId.get(id);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("rowId",rowId);
            jsonObject.put("result",execResult.get(id));
            jsonArray.add(jsonObject);
        }
        return JSONArray.toJSONString(jsonArray);
    }
}
