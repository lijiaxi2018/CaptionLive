package com.aguri.captionlive.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/*")
@Order(1)
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 允许的域名
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 允许的方法
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        // 允许的请求头
        response.setHeader("Access-Control-Allow-Headers", "*");
        // 允许包含凭证（如cookies）
        response.setHeader("Access-Control-Allow-Credentials", "true");
        // 最大缓存时间
        response.setHeader("Access-Control-Max-Age", "3600");
        // 处理预检请求
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        filterChain.doFilter(request, response);
    }

    // 其他方法如init()和destroy()可以保持为空实现
}
