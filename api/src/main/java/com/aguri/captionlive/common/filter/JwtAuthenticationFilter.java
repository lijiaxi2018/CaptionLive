//package com.aguri.captionlive.common.filter;
//
//import com.aguri.captionlive.common.JwtTokenProvider;
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//@WebFilter(urlPatterns = "/*")
//@Order(1)
//public class JwtAuthenticationFilter implements Filter {
//
//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;
//
//    private static final String LOGIN_PATH = "/login";
//    private static final String API_PATH_PREFIX = "/api/";
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//
//        String requestURI = request.getRequestURI();
//        if (requestURI.startsWith(API_PATH_PREFIX)) {
//            // 获取请求中的JWT
//            String token = request.getHeader("Authorization");
//
//            if (token != null && jwtTokenProvider.validateToken(token)) {
//                // 用户已登录，验证JWT通过
//                request.setAttribute("username", jwtTokenProvider.parseUserNameFromToken(token));
//            } else {
//                // 用户未登录或JWT验证失败
////                response.sendRedirect(LOGIN_PATH);
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                return;
//            }
//        }
//
//
//        // 将请求传递给下一个过滤器或目标资源
//        filterChain.doFilter(request, response);
//    }
//}
//
