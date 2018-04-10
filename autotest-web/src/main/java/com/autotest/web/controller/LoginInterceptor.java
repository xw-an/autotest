package com.autotest.web.controller;

import com.autotest.core.model.TestUser;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        HttpSession session=request.getSession();
        TestUser loginUser=(TestUser)session.getAttribute("loginUser");
        if(loginUser!=null&&!loginUser.equals("")){
            return true;
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
