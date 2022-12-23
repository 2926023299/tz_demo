package com.example.tz_demo.interceptor;

import com.example.tz_demo.common.BaseContext;
import com.example.tz_demo.entity.ManageUser;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AppTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //String userId = request.getHeader("userId");
        //String userId = request.getParameter("userId");
        String userId = (String)request.getSession().getAttribute("userId");
        if(userId != null){
            //存入到当前线程中
            ManageUser user = new ManageUser();
            user.setId(userId);
            BaseContext.setUser(user);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContext.removeUser();
    }
}
