package com.aguri.captionlive.common.filter;

import com.aguri.captionlive.common.JwtTokenProvider;
import com.aguri.captionlive.model.User;
import com.aguri.captionlive.repository.UserRepository;
import com.aguri.captionlive.service.UserService;
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
import java.util.Optional;

@Component
@WebFilter(urlPatterns = "/*")
@Order(2)
public class JwtAuthenticationFilter implements Filter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    private static final List<String> EXCLUDED_URLS = Arrays.asList("/api/login", "/api/signUp", "/swagger-ui", "/v3");

    private static final String LOGIN_PATH = "/myhome";

    private static final Boolean OPEN = Boolean.TRUE;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (!OPEN) {
            request.setAttribute("username", "this request dependence on Authorization Header, please open this filter: " + JwtTokenProvider.class.getName());
            request.setAttribute("username", "this request dependence on Authorization Header, please open this filter: " + JwtTokenProvider.class.getName());
            filterChain.doFilter(request, response);
            return;
        }

        String requestURI = request.getRequestURI();
        if (isExcludedUrl(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String username = jwtTokenProvider.parseUserNameFromToken(token);
        Optional<User> byUsername = userRepository.findByUsername(username);
        boolean present = byUsername.isPresent();
        if (!present) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        User operator = byUsername.get();
        request.setAttribute("username", username);
        request.setAttribute("operator", operator);

        filterChain.doFilter(request, response);
        return;
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

