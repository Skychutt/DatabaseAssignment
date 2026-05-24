package com.COMP2013J.assignment.servlet;

import com.COMP2013J.assignment.entity.Student;
import com.COMP2013J.assignment.entity.Teacher;
import com.COMP2013J.assignment.service.ClazzService;
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

    private final StudentService studentService = new StudentService();
    private final TeacherService teacherService = new TeacherService();
    private final ClazzService clazzService = new ClazzService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");

        String captcha = req.getParameter("captcha");
        Object sessionCaptcha = req.getSession().getAttribute("captcha");
        if (captcha == null || sessionCaptcha == null
                || !captcha.equalsIgnoreCase(String.valueOf(sessionCaptcha))) {
            resp.getWriter().write(ApiResult.json(false, "验证码错误！"));
            return;
        }
        req.getSession().removeAttribute("captcha");

        String usertype = req.getParameter("usertype");
        if (usertype == null) {
            resp.getWriter().write(ApiResult.json(false, "用户类型不能为空！"));
            return;
        }

        if ("admin".equals(usertype)) {
            resp.getWriter().write(ApiResult.json(false, "不允许自助注册管理员，请联系系统管理员添加账号。"));
            return;
        }

        if ("emp".equals(usertype)) {
            String sno = req.getParameter("username");
            String password = req.getParameter("password");
            String name = req.getParameter("name");
            String clazzno = req.getParameter("clazzno");

            if (clazzService.getByClazzno(clazzno == null ? "" : clazzno.trim()) == null) {
                resp.getWriter().write(ApiResult.json(false, "班级编号不存在，请填写正确的班级编号。"));
                return;
            }

            Student student = new Student();
            student.setSno(sno);
            student.setPassword(password);
            student.setName(name);
            student.setClazzno(clazzno);
            student.setGender("m");
            student.setAge(18);
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
