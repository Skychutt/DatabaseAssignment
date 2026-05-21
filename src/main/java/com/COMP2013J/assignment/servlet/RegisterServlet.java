package com.COMP2013J.assignment.servlet;

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
import java.io.IOException;
import java.sql.Date;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final AdminService adminService = new AdminService();
    private final StudentService studentService = new StudentService();
    private final TeacherService teacherService = new TeacherService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");

        String usertype = req.getParameter("usertype");
        if (usertype == null) {
            resp.getWriter().write(ApiResult.json(false, "用户类型不能为空！"));
            return;
        }

        if ("admin".equals(usertype)) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            String msg = adminService.register(username, password);
            if (msg != null) {
                resp.getWriter().write(ApiResult.json(false, msg));
            } else {
                resp.getWriter().write(ApiResult.json(true, "注册成功"));
            }
            return;
        }

        if ("emp".equals(usertype)) {
            // 复用登录页：username 当作学生学号 sno
            String sno = req.getParameter("username");
            String password = req.getParameter("password");
            String name = req.getParameter("name");
            String clazzno = req.getParameter("clazzno");

            // StudentService.insert 只强制校验 sno/password/name/clazzno；
            // 其它字段我们给默认值，避免数据库 NOT NULL 约束导致插入失败。
            Student student = new Student();
            student.setSno(sno);
            student.setPassword(password);
            student.setName(name);
            student.setClazzno(clazzno);
            student.setGender("m");
            student.setAge(0);
            student.setTele("");
            student.setAddress("");
            student.setEnterdate(new Date(System.currentTimeMillis()));

            String msg = studentService.insert(student);
            if (msg != null) {
                resp.getWriter().write(ApiResult.json(false, msg));
            } else {
                resp.getWriter().write(ApiResult.json(true, "注册成功"));
            }
            return;
        }

        if ("teacher".equals(usertype)) {
            String tno = req.getParameter("username");
            String password = req.getParameter("password");
            String tname = req.getParameter("name");

            Teacher teacher = new Teacher();
            teacher.setTno(tno);
            teacher.setPassword(password);
            teacher.setTname(tname);

            String msg = teacherService.insert(teacher);
            if (msg != null) {
                resp.getWriter().write(ApiResult.json(false, msg));
            } else {
                resp.getWriter().write(ApiResult.json(true, "注册成功"));
            }
            return;
        }

        resp.getWriter().write(ApiResult.json(false, "未知用户类型！"));
    }
}

