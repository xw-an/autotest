package com.autotest.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/TestCase")
public class TestCaseController {

    @RequestMapping(value="/TestCaseManage",method=RequestMethod.GET)
    public String showCase(){

        return "TestCaseManage";
    }
}
