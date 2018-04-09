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

@Controller
@RequestMapping("/TestUser")
public class TestUserController {

    @Autowired
    private ITestUserService testUserService;

    @RequestMapping("/login")
    public String showLogin(){
        return "login";
    }

    @RequestMapping("/loginUser")
    public String loginUser(@RequestParam("name") String name, @RequestParam("password") String password, Model model){
        boolean result=false;
        try {
            TestUser loginUser=testUserService.selectUser(name,password);
            if(loginUser!=null&&!loginUser.equals("")){ result=true;}
        } catch (Exception e) {
            model.addAttribute("errorMsg",e.getMessage());
        }
        if(result){
            //TODO 需要将当前用户保存在session中
            //登陆成功跳转到用例管理界面
            return "redirect:/TestCaseManage";
        }else{
            //登陆失败返回登陆页面
            return "login";
        }
    }


   /* @RequestMapping("/loginUser")
    @ResponseBody
    public String loginUser(@RequestBody JSONObject user){
        JSONObject jsonResult=new JSONObject();
        String name=user.getString("name");
        String password=user.getString("password");
        try {
            TestUser loginUser=testUserService.selectUser(name,password);
            if(loginUser!=null&&!loginUser.equals("")){
                SystemParameters.setLoginUser(loginUser);//将当前用户保存到threadlocal中
                jsonResult.put("result",true);
            }else{
                jsonResult.put("result",false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.put("result",false);
            jsonResult.put("message",e.getMessage());
        }
        return JSONObject.toJSONString(jsonResult);
    }*/


}
