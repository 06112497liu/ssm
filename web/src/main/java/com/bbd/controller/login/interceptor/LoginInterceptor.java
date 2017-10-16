/**
 * BBD Service Inc
 * AllRight Reserved @2017
 */
package com.bbd.controller.login.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 * 
 * @author xc
 * @version $Id: LoginInterceptor.java, v 0.1 2016年12月5日 下午1:16:36 xc Exp $
 */
public class LoginInterceptor implements HandlerInterceptor {
    public static final Logger logger       = LoggerFactory.getLogger(LoginInterceptor.class);

    /** json处理工具 */
    private ObjectMapper       objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
