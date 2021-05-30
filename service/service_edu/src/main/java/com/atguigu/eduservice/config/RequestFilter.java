package com.atguigu.eduservice.config;

import org.apache.commons.lang3.StringUtils;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@WebFilter(filterName = "RequestFilter", urlPatterns = {"/*"})
public class RequestFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        System.out.println("FilterDemo1被执行了....");
        // 设置允许跨域访问的域，*表示支持所有的来源
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 设置允许跨域访问的方法
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        //response.setHeader("Access-Control-Allow-Headers", "DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Authorization,Ice,X-Token");
        response.setHeader("Access-Control-Expose-Headers", "x-carry-down,x-token");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        HttpServletRequest request = (HttpServletRequest) req;
        if ("OPTIONS".equals(request.getMethod())) {
            response.setHeader("Access-Control-Max-Age", "1728000");
            response.setHeader("Content-Type", "text/plain charset=UTF-8");
            response.setHeader("Content-Length", "0");
            System.out.println("OPTIONS被执行了....");
           // response.setStatus(response.SC_OK);
           // return;
        }

        chain.doFilter(req, res);
        System.out.println("doFilter被执行了....");
    }
    //可访问域名
    private static List<String> ALLOW_SUBDOMAIN_REGEX = null;

    @Override
    public void init(FilterConfig filterConfig) {
        ALLOW_SUBDOMAIN_REGEX = new ArrayList<>();
        ALLOW_SUBDOMAIN_REGEX.add("https?://localhost");
        ALLOW_SUBDOMAIN_REGEX.add("https?://localhost:\\d+");
        ALLOW_SUBDOMAIN_REGEX.add("http?://localhost:\\d+");
        ALLOW_SUBDOMAIN_REGEX.add("http?://127.0.0.1:\\d+");
    }

    }
