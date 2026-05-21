package com.COMP2013J.assignment.servlet;

import com.COMP2013J.assignment.entity.Admin;
import com.COMP2013J.assignment.entity.Student;
import com.COMP2013J.assignment.entity.Teacher;
import com.COMP2013J.assignment.service.AdminService;
import com.COMP2013J.assignment.service.StudentService;
import com.COMP2013J.assignment.service.TeacherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private final StudentService studentService = new StudentService();
    private final AdminService adminService = new AdminService();
    private final TeacherService teacherService = new TeacherService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("role") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String role = String.valueOf(session.getAttribute("role"));
        if ("student".equals(role)) {
            Student user = (Student) session.getAttribute("user");
            if (user == null) {
                resp.sendRedirect(req.getContextPath() + "/login.jsp");
                return;
            }
            Student fresh = studentService.getBySno(user.getSno());
            req.setAttribute("student", fresh == null ? user : fresh);
        } else if ("admin".equals(role)) {
            Admin user = (Admin) session.getAttribute("user");
            if (user == null) {
                resp.sendRedirect(req.getContextPath() + "/login.jsp");
                return;
            }
            Admin fresh = adminService.getByUsername(user.getUsername());
            req.setAttribute("admin", fresh == null ? user : fresh);
        } else if ("teacher".equals(role)) {
            Teacher user = (Teacher) session.getAttribute("user");
            if (user == null) {
                resp.sendRedirect(req.getContextPath() + "/login.jsp");
                return;
            }
            Teacher fresh = teacherService.getByTno(user.getTno());
            req.setAttribute("teacher", fresh == null ? user : fresh);
        }

        req.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(req, resp);
    }
}

