package com.COMP2013J.assignment.security;

import jakarta.servlet.http.HttpSession;

public final class RoleHelper {

    private RoleHelper() {
    }

    public static String role(HttpSession session) {
        if (session == null) {
            return null;
        }
        Object r = session.getAttribute("role");
        return r == null ? null : String.valueOf(r);
    }

    public static boolean isAdmin(HttpSession session) {
        return "admin".equals(role(session));
    }

    public static boolean isTeacher(HttpSession session) {
        return "teacher".equals(role(session));
    }

    public static boolean isStudent(HttpSession session) {
        return "student".equals(role(session));
    }

    public static boolean isAdminOrTeacher(HttpSession session) {
        return isAdmin(session) || isTeacher(session);
    }
}
