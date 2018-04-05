package com.autotest.web.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/TestLogManage")
public class TestLogController {

    @RequestMapping
    public String showTestLog(){
        return "TestLogManage";
    }


    @RequestMapping("/LogList")
    @ResponseBody
    public String showLogList(@RequestBody JSONObject logParams){
        return null;
    }

    @RequestMapping("/LogDetail")
    @ResponseBody
    public String showLogDetail(@PathVariable String execId){
        return null;
    }
}
