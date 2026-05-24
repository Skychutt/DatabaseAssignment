package com.COMP2013J.assignment.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.security.SecureRandom;
import java.util.Base64;

public final class CsrfUtil {

    public static final String SESSION_ATTR = "csrfToken";
    public static final String PARAM_NAME = "_csrf";

    private static final SecureRandom RANDOM = new SecureRandom();

    private CsrfUtil() {
    }

    public static String ensureToken(HttpSession session) {
        if (session == null) {
            return null;
        }
        Object existing = session.getAttribute(SESSION_ATTR);
        if (existing instanceof String && !((String) existing).isEmpty()) {
            return (String) existing;
        }
        String token = generate();
        session.setAttribute(SESSION_ATTR, token);
        return token;
    }

    public static String generate() {
        byte[] buf = new byte[24];
        RANDOM.nextBytes(buf);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(buf);
    }

    public static boolean validate(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return false;
        }
        Object expected = session.getAttribute(SESSION_ATTR);
        if (!(expected instanceof String) || ((String) expected).isEmpty()) {
            return false;
        }
        String actual = req.getParameter(PARAM_NAME);
        if (actual == null || actual.isEmpty()) {
            actual = req.getHeader("X-CSRF-Token");
        }
        return expected.equals(actual);
    }

    public static boolean isExemptPath(String contextPath, String uri) {
        if (uri.equals(contextPath + "/login")) {
            return true;
        }
        if (uri.equals(contextPath + "/register")) {
            return true;
        }
        if (uri.equals(contextPath + "/captcha")) {
            return true;
        }
        return false;
    }
}
