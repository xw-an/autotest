package com.autotest.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.autotest.core.model.SystemParameters;
import com.autotest.core.model.TestUser;
import com.autotest.service.common.ITestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/TestUser")
public class TestUserController {

    @Autowired
    private ITestUserService testUserService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/login")
    public String showLogin(){
        return "login";
    }

    @RequestMapping("/loginUser")
    public String loginUser(@RequestParam("name") String name, @RequestParam("password") String password, Model model){
        boolean result=false;
        try {
            TestUser loginUser=testUserService.selectUser(name,password);
            if(loginUser!=null&&!loginUser.equals("")){
                HttpSession session=request.getSession();
                session.setAttribute("loginUser",loginUser);//登陆信息保存在session中
                result=true;
            }
        } catch (Exception e) {
            model.addAttribute("errorMsg",e.getMessage());
        }
        if(result){
            //登陆成功跳转到用例管理界面
            return "redirect:/TestCaseManage";
        }else{
            //登陆失败返回登陆页面
            return "login";
        }
    }

    @RequestMapping("/logout")
    public String logoutUser(){
        HttpSession session=request.getSession();
        session.removeAttribute("loginUser");
        return "redirect:/TestUser/login";
    }
}
