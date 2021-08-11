package com.sakurabird.cangxi.system.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.sakurabird.cangxi.system.data.response.ResponseData;
import com.sakurabird.cangxi.system.enums.ErrorCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
*@author vsans@sina.cn
*@date 2020/7/2
*@desc 自定义过滤器
**/
@Log4j2
@Component
@WebFilter(filterName = "custom", urlPatterns = "/*")
public class CustomFilter implements Filter {

    /**
    拦截列表,用","分开
     */
    private final static String IGPATH = "/user/login,/user/register,/user/areas,/user/checkname";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     *主拦截器
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        setOragin(res,req);
        String path = req.getRequestURI();
//        log.info("RequestPath:"+path);
        //验证拦截
        if (checkFilterPath(path)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String token=req.getHeader("token");
        //用户未登录
        if(StrUtil.isBlank(token)){
            writeResult(res, ResponseData.error(ErrorCode.USER_UN_LOGIN.getCode(),ErrorCode.USER_UN_LOGIN.getMsg()));
            return;
        }
        //登录信息无效
        if(redisTemplate.boundValueOps(token).get()==null){
            writeResult(res, ResponseData.error(ErrorCode.USER_UN_LOGIN.getCode(),ErrorCode.USER_UN_LOGIN.getMsg()));
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }

    /**
     * 对页面输出方法
     */
    void writeResult(HttpServletResponse res,Object data) throws IOException {
        Writer out = res.getWriter();
        res.setContentType("application/json;charset=utf-8");
        out.write(JSONUtil.toJsonStr(data));
        out.close();
    }



    /**
     *    验证是否需要忽略拦截
     */
    boolean checkFilterPath(String path) {
        String[] pathlist = IGPATH.split(",");
        if (path == null || "".equals(path)) {
            return false;
        }
        for (String st : pathlist) {
            if (path.startsWith(st)) {
                return true;
            }
            if (path.contains("swagger")||path.contains("doc")||path.contains("webjar")||path.contains("favicon")){
                return true;
            }
        }
        return false;
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
        response.setHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-Type,Cookie,Accept,token");
    }
}