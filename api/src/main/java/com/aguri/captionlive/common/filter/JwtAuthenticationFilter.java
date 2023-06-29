package com.aguri.captionlive.common.filter;

import com.aguri.captionlive.common.JwtTokenProvider;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@WebFilter(urlPatterns = "/*")
@Order(1)
public class JwtAuthenticationFilter implements Filter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static final List<String> EXCLUDED_URLS = Arrays.asList("/api/login", "/api/signUp");
    private static final String LOGIN_PATH = "/myhome";

    private static final Boolean OPEN = Boolean.TRUE;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "3600");

        if (OPEN) {

            String requestURI = request.getRequestURI();
            if (!isExcludedUrl(requestURI)) {
                // 获取请求中的JWT
                String token = request.getHeader("Authorization");

                if (token != null && jwtTokenProvider.validateToken(token)) {
                    // 用户已登录，验证JWT通过
                    request.setAttribute("username", jwtTokenProvider.parseUserNameFromToken(token));
                } else {
                    // 用户未登录或JWT验证失败
                    response.sendRedirect(LOGIN_PATH);
//                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }
        } else {
            request.setAttribute("username", "this request dependence on Authorization Header, please open this filter: " + JwtTokenProvider.class.getName());
        }


        // 将请求传递给下一个过滤器或目标资源
        filterChain.doFilter(request, response);
    }

    private boolean isExcludedUrl(String requestURI) {
        for (String excludedUrl : EXCLUDED_URLS) {
            if (requestURI.startsWith(excludedUrl)) {
                return true;
            }
        }
        return false;
    }
}

