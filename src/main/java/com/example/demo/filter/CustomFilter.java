package com.example.demo.filter;

import lombok.extern.log4j.Log4j2;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


/**
*@author vsans@sina.cn
*@date 2020/7/2
*@desc 自定义过滤器
**/
@WebFilter(filterName = "custom", urlPatterns = "/*")
@Log4j2
public class CustomFilter implements Filter {


    /**
     *主拦截器
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        setOragin(res,req);
        String path = req.getRequestURI();
        log.info("RequestPath:"+path);
        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }

    /**
     *跨域设置
     */
    private void setOragin(HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,PUT");
        response.setHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-Type,Cookie,Accept");
    }
}