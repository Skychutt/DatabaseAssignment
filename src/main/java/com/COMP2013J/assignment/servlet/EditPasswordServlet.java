package com.COMP2013J.assignment.servlet;

import com.COMP2013J.assignment.entity.Admin;
import com.COMP2013J.assignment.entity.Student;
import com.COMP2013J.assignment.entity.Teacher;
import com.COMP2013J.assignment.service.AdminService;
import com.COMP2013J.assignment.service.StudentService;
import com.COMP2013J.assignment.service.TeacherService;
import com.COMP2013J.assignment.utils.ApiResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/editPassword")
public class EditPasswordServlet extends HttpServlet {
    private final StudentService studentService = new StudentService();
    private final AdminService adminService = new AdminService();
    private final TeacherService teacherService = new TeacherService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("role") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/view/edit-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("role") == null) {
            resp.getWriter().write(ApiResult.json(false, "未登录"));
            return;
        }

        String role = String.valueOf(session.getAttribute("role"));
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");

        if (newPassword == null || newPassword.trim().isEmpty()) {
            resp.getWriter().write(ApiResult.json(false, "新密码不可为空"));
            return;
        }

        if ("student".equals(role)) {
            Object obj = session.getAttribute("user");
            if (!(obj instanceof Student)) {
                resp.getWriter().write(ApiResult.json(false, "会话用户无效"));
                return;
            }

            Student current = (Student) obj;
            if (oldPassword == null || !oldPassword.equals(current.getPassword())) {
                resp.getWriter().write(ApiResult.json(false, "旧密码错误"));
                return;
            }

            Student update = new Student();
            update.setSno(current.getSno());
            update.setPassword(newPassword.trim());
            String msg = studentService.update(update);
            if (msg != null) {
                resp.getWriter().write(ApiResult.json(false, msg));
                return;
            }

            current.setPassword(newPassword.trim());
            session.setAttribute("user", current);
            resp.getWriter().write(ApiResult.json(true, "修改成功"));
            return;
        }

        if ("admin".equals(role)) {
            Object obj = session.getAttribute("user");
            if (!(obj instanceof Admin)) {
                resp.getWriter().write(ApiResult.json(false, "会话用户无效"));
                return;
            }
            Admin current = (Admin) obj;
            if (oldPassword == null || !oldPassword.equals(current.getPassword())) {
                resp.getWriter().write(ApiResult.json(false, "旧密码错误"));
                return;
            }

            String msg = adminService.updatePassword(current.getUsername(), newPassword.trim());
            if (msg != null) {
                resp.getWriter().write(ApiResult.json(false, msg));
                return;
            }
            current.setPassword(newPassword.trim());
            session.setAttribute("user", current);
            resp.getWriter().write(ApiResult.json(true, "修改成功"));
            return;
        }

        if ("teacher".equals(role)) {
            Object obj = session.getAttribute("user");
            if (!(obj instanceof Teacher)) {
                resp.getWriter().write(ApiResult.json(false, "会话用户无效"));
                return;
            }

            Teacher current = (Teacher) obj;
            if (oldPassword == null || !oldPassword.equals(current.getPassword())) {
                resp.getWriter().write(ApiResult.json(false, "旧密码错误"));
                return;
            }

            Teacher update = new Teacher();
            update.setTno(current.getTno());
            update.setPassword(newPassword.trim());
            String msg = teacherService.update(update);
            if (msg != null) {
                resp.getWriter().write(ApiResult.json(false, msg));
                return;
            }

            current.setPassword(newPassword.trim());
            session.setAttribute("user", current);
            resp.getWriter().write(ApiResult.json(true, "修改成功"));
            return;
        }

        resp.getWriter().write(ApiResult.json(false, "未知角色"));
    }
}

