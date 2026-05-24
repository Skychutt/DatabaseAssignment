package com.COMP2013J.assignment.servlet;

import com.COMP2013J.assignment.entity.Admin;
import com.COMP2013J.assignment.entity.Student;
import com.COMP2013J.assignment.entity.Teacher;
import com.COMP2013J.assignment.security.PasswordUtil;
import com.COMP2013J.assignment.service.AdminService;
import com.COMP2013J.assignment.service.StudentService;
import com.COMP2013J.assignment.service.TeacherService;
import com.COMP2013J.assignment.utils.ApiResult;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final AdminService adminService = new AdminService();
    private final StudentService studentService = new StudentService();
    private final TeacherService teacherService = new TeacherService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");

        try {
            handleLogin(req, resp);
        } catch (SQLException e) {
            resp.getWriter().print(ApiResult.json(false,
                    "数据库连接失败，请检查 MySQL 是否启动，并确认 src/main/resources/jdbc.properties 中的账号密码正确。"));
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().print(ApiResult.json(false, "登录处理异常，请查看 Tomcat 控制台日志。"));
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        String captcha = req.getParameter("captcha");
        Object sessionCaptcha = req.getSession().getAttribute("captcha");
        if (captcha == null || sessionCaptcha == null
                || !captcha.trim().equalsIgnoreCase(String.valueOf(sessionCaptcha).trim())) {
            resp.getWriter().print(ApiResult.json(false, "验证码错误，请按图片重新输入（点击图片可刷新）。"));
            return;
        }
        req.getSession().removeAttribute("captcha");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String usertype = req.getParameter("usertype");
        if (usertype == null || usertype.isEmpty()) {
            resp.getWriter().print(ApiResult.json(false, "请选择登录角色"));
            return;
        }

        if ("admin".equals(usertype)) {
            Admin admin = adminService.getByUsername(username);
            if (admin == null) {
                resp.getWriter().print(ApiResult.json(false, "用户不存在"));
                return;
            }
            if (!PasswordUtil.matches(password, admin.getPassword())) {
                resp.getWriter().print(ApiResult.json(false, "密码错误，登录失败"));
                return;
            }
            upgradeAdminPassword(admin, password);
            req.getSession().setAttribute("role", "admin");
            req.getSession().setAttribute("user", admin);
            resp.getWriter().print(ApiResult.json(true, "登录成功"));
            return;
        }
        if ("emp".equals(usertype)) {
            Student student = studentService.getBySno(username);
            if (student == null) {
                resp.getWriter().print(ApiResult.json(false, "用户不存在"));
                return;
            }
            if (!PasswordUtil.matches(password, student.getPassword())) {
                resp.getWriter().print(ApiResult.json(false, "密码错误，登录失败"));
                return;
            }
            upgradeStudentPassword(student, password);
            req.getSession().setAttribute("role", "student");
            req.getSession().setAttribute("user", student);
            resp.getWriter().print(ApiResult.json(true, "登录成功"));
            return;
        }
        if ("teacher".equals(usertype)) {
            Teacher teacher = teacherService.getByTno(username);
            if (teacher == null) {
                resp.getWriter().print(ApiResult.json(false, "用户不存在"));
                return;
            }
            if (!PasswordUtil.matches(password, teacher.getPassword())) {
                resp.getWriter().print(ApiResult.json(false, "密码错误，登录失败"));
                return;
            }
            upgradeTeacherPassword(teacher, password);
            req.getSession().setAttribute("role", "teacher");
            req.getSession().setAttribute("user", teacher);
            resp.getWriter().print(ApiResult.json(true, "登录成功"));
            return;
        }
        resp.getWriter().print(ApiResult.json(false, "未知登录角色"));
    }

    private void upgradeAdminPassword(Admin admin, String rawPassword) throws SQLException {
        String upgraded = PasswordUtil.upgradeIfNeeded(rawPassword, admin.getPassword());
        if (!upgraded.equals(admin.getPassword())) {
            adminService.updatePassword(admin.getUsername(), rawPassword);
            admin.setPassword(upgraded);
        }
    }

    private void upgradeStudentPassword(Student student, String rawPassword) throws SQLException {
        String upgraded = PasswordUtil.upgradeIfNeeded(rawPassword, student.getPassword());
        if (!upgraded.equals(student.getPassword())) {
            Student patch = new Student();
            patch.setSno(student.getSno());
            patch.setPassword(rawPassword);
            studentService.update(patch);
            student.setPassword(upgraded);
        }
    }

    private void upgradeTeacherPassword(Teacher teacher, String rawPassword) throws SQLException {
        String upgraded = PasswordUtil.upgradeIfNeeded(rawPassword, teacher.getPassword());
        if (!upgraded.equals(teacher.getPassword())) {
            Teacher patch = new Teacher();
            patch.setTno(teacher.getTno());
            patch.setPassword(rawPassword);
            teacherService.update(patch);
            teacher.setPassword(upgraded);
        }
    }
}
