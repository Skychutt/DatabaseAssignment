package com.COMP2013J.assignment.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * 登录保护：未登录访问后台页面，统一跳回登录页。
 */
@WebFilter(urlPatterns = "/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String contextPath = req.getContextPath();
        String uri = req.getRequestURI();

        // 放行：登录页、登录接口、验证码、静态资源
        boolean isLoginPage = (contextPath + "/login.jsp").equals(uri);
        boolean isLoginApi = (contextPath + "/login").equals(uri);
        boolean isRegisterApi = (contextPath + "/register").equals(uri);
        boolean isCaptcha = (contextPath + "/captcha").equals(uri);
        boolean isLogout = (contextPath + "/logout").equals(uri);
        boolean isStaticAssets = uri.startsWith(contextPath + "/assets/") || uri.startsWith(contextPath + "/favicon.ico");

        if (isLoginPage || isLoginApi || isRegisterApi || isCaptcha || isStaticAssets || isLogout) {
            chain.doFilter(req, resp);
            return;
        }

        HttpSession session = req.getSession(false);
        Object role = session == null ? null : session.getAttribute("role");
        if (role == null) {
            resp.sendRedirect(contextPath + "/login.jsp");
            return;
        }

        chain.doFilter(req, resp);
    }
}

