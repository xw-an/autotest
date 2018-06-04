package com.autotest.web.controller;

import com.autotest.core.model.TestUser;
import com.autotest.service.common.ITestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private ITestUserService testUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        HttpSession session=request.getSession();
        TestUser loginUser=(TestUser)session.getAttribute("loginUser");
        if(loginUser!=null&&!loginUser.equals("")){
            return true;
        }else{
            String loginCookieUserId = "";
            String loginCookiePassword = "";
            //获取cookie信息，如果cookie信息
            Cookie[] cookies=request.getCookies();
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("loginUserId")){
                    loginCookieUserId=cookie.getValue();
                }else if(cookie.getName().equals("loginPassword")){
                    loginCookiePassword=cookie.getValue();
                }
            }
            if(loginCookieUserId!=""&&loginCookiePassword!=""){
                loginUser=testUserService.selectUser(loginCookieUserId,loginCookiePassword);
                if(loginUser!=null){
                    session.setAttribute("loginUser",loginUser);
                    return true;
                }
            }
        }
        request.setAttribute("errorMsg","您还未登陆");
        ServletContext context = request.getSession().getServletContext();
        response.sendRedirect(context.getContextPath() + "/TestUser/login");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
