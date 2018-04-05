package com.autotest.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/TestToolManage")
public class TestToolController {

    @RequestMapping
    public String showTestToolManage(){
        return "TestToolManage";
    }

    @RequestMapping("/packageTool")
    public String showPackgeTool(){
        return "packageTool";
    }
}
