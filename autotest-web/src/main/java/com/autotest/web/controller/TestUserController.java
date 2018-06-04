package com.autotest.web.controller;

import com.autotest.core.model.TestUser;
import com.autotest.service.common.ITestUserService;
import com.autotest.web.config.CookiesParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public String loginUser(@RequestParam String name, @RequestParam String password,@RequestParam(required=false) String isSaveCookie,HttpServletResponse response,Model model){
        boolean result=false;
        try {
            TestUser loginUser=testUserService.selectUser(name,password);
            if(loginUser!=null&&!loginUser.equals("")){
                HttpSession session=request.getSession();
                session.setAttribute("loginUser",loginUser);//登陆信息保存在session中

                if(isSaveCookie!=null&&isSaveCookie.equals("on")){
                    //若选中记住密码，则保存cookies信息
                    int autoLoginTimeout=CookiesParameters.autoLoginTimeout;
                    if(autoLoginTimeout>0){
                        Cookie userNameCookie = new Cookie("loginUserId", loginUser.getUserId());
                        Cookie passwordCookie = new Cookie("loginPassword", loginUser.getPassword());
                        userNameCookie.setMaxAge(autoLoginTimeout*60*60*24);//转换成天数
                        userNameCookie.setPath("/");
                        passwordCookie.setMaxAge(autoLoginTimeout*60*60*24);//转换成天数
                        passwordCookie.setPath("/");
                        response.addCookie(userNameCookie);
                        response.addCookie(passwordCookie);
                    }
                }
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
    public String logoutUser(HttpServletResponse response){
        HttpSession session=request.getSession();
        session.removeAttribute("loginUser");//移除session
        //删除cookies
        Cookie[] cookies=request.getCookies();
        for(Cookie c:cookies){
            if(c.getName().equals("loginUserId")||c.getName().equals("loginPassword")){
                c.setMaxAge(0);
                c.setValue(null);
                c.setPath("/");
                response.addCookie(c);
            }
        }
        return "redirect:/TestUser/login";
    }
}
