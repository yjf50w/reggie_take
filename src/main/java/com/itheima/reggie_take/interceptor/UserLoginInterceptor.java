package com.itheima.reggie_take.interceptor;

import com.itheima.reggie_take.common.BaseContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @title: LoginInterceptor
 * @Author Junfang Yuan
 * @Date: 2022/6/7 15:28
 * @Version 1.0
 */
public class UserLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long userId = (Long) request.getSession().getAttribute("user");
        if (userId!=null){
            BaseContext.setCurrentId(userId);
            return true;
        }
//        request.getRequestDispatcher("/backend/page/login/login.html").forward(request, response);
        response.sendRedirect("/front/page/login.html");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
