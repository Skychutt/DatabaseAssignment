package com.COMP2013J.assignment.servlet;

import com.COMP2013J.assignment.security.RoleHelper;
import com.COMP2013J.assignment.service.TeacherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 面向全体登录用户的教师公开名录（学生侧栏「教师通讯录」入口）。
 */
@WebServlet("/teacherDirectory")
public class TeacherDirectoryServlet extends HttpServlet {

    private final TeacherService teacherService = new TeacherService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession(false) == null || !RoleHelper.isStudent(req.getSession(false))
                && !RoleHelper.isTeacher(req.getSession(false))
                && !RoleHelper.isAdmin(req.getSession(false))) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        req.setAttribute("teacherProfiles", teacherService.listPublicProfiles());
        req.getRequestDispatcher("/WEB-INF/view/teacher-directory.jsp").forward(req, resp);
    }
}
