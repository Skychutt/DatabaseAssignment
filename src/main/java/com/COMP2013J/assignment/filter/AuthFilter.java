package com.COMP2013J.assignment.filter;

import com.COMP2013J.assignment.security.CsrfUtil;
import com.COMP2013J.assignment.security.RoleHelper;
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
 * 登录保护、角色路径约束、CSRF 校验。
 */
@WebFilter(urlPatterns = "/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String contextPath = req.getContextPath();
        String uri = req.getRequestURI();

        boolean isLoginPage = (contextPath + "/login.jsp").equals(uri);
        boolean isLoginApi = (contextPath + "/login").equals(uri);
        boolean isRegisterApi = (contextPath + "/register").equals(uri);
        boolean isCaptcha = (contextPath + "/captcha").equals(uri);
        boolean isLogout = (contextPath + "/logout").equals(uri);
        boolean isStaticAssets = uri.startsWith(contextPath + "/assets/") || uri.startsWith(contextPath + "/favicon.ico");

        if (isLoginPage || isLoginApi || isRegisterApi || isCaptcha || isStaticAssets || isLogout) {
            if (isLoginPage) {
                HttpSession session = req.getSession(true);
                CsrfUtil.ensureToken(session);
            }
            chain.doFilter(req, resp);
            return;
        }

        HttpSession session = req.getSession(false);
        Object role = session == null ? null : session.getAttribute("role");
        if (role == null) {
            resp.sendRedirect(contextPath + "/login.jsp");
            return;
        }

        CsrfUtil.ensureToken(session);

        if ("POST".equalsIgnoreCase(req.getMethod()) && !CsrfUtil.isExemptPath(contextPath, uri)) {
            if (!CsrfUtil.validate(req)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid CSRF token");
                return;
            }
        }

        if (uri.equals(contextPath + "/directory") && RoleHelper.isStudent(session)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "权限不足");
            return;
        }

        chain.doFilter(req, resp);
    }
}
