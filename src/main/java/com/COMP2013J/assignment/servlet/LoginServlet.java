package com.COMP2013J.assignment.servlet;

import com.COMP2013J.assignment.entity.*;
import com.COMP2013J.assignment.service.*;
import com.COMP2013J.assignment.utils.ApiResult;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
/**
 * 用于登录的Servlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    AdminService adminService = new AdminService();
    StudentService studentService = new StudentService();
    TeacherService teacherService = new TeacherService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");// 设置编码，否则从前端获取参数乱码
        resp.setContentType("application/json; charset=utf-8");

        //获取请求参数
        String captcha = req.getParameter("captcha");
        Object sessionCaptcha = req.getSession().getAttribute("captcha");
        if(captcha == null || !captcha.equalsIgnoreCase((String) sessionCaptcha)){
            resp.getWriter().print(ApiResult.json(false,"验证码错误！"));
            return;
        }

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String usertype = req.getParameter("usertype");
        if (usertype == null || usertype.isEmpty()) {
            resp.getWriter().print(ApiResult.json(false, "请选择登录角色"));
            return;
        }
        //判断角色
        if ("admin".equals(usertype)) {
            //根据账号获取数据库信息
            Admin admin = adminService.getByUsername(username);
            if(admin == null){
                resp.getWriter().print(ApiResult.json(false,"用户不存在"));
                return;
            }
            if(admin.getPassword().equals(password)){
                req.getSession().setAttribute("role","admin");
                req.getSession().setAttribute("user",admin);
                resp.getWriter().print(ApiResult.json(true,"登录成功"));
                return;
            }else{
                resp.getWriter().print(ApiResult.json(false,"密码错误，登录失败"));
                return;
            }
        }
        if ("emp".equals(usertype)) {
            Student student = studentService.getBySno(username);
            if(student == null){
                resp.getWriter().print(ApiResult.json(false,"用户不存在"));
                return;
            }
            if(student.getPassword().equals(password)){
                req.getSession().setAttribute("role","student");
                req.getSession().setAttribute("user",student);
                resp.getWriter().print(ApiResult.json(true,"登录成功"));
                return;
            }else{
                resp.getWriter().print(ApiResult.json(false,"密码错误，登录失败"));
                return;
            }
        }
        if ("teacher".equals(usertype)) {
            Teacher teacher = teacherService.getByTno(username);
            if (teacher == null) {
                resp.getWriter().print(ApiResult.json(false, "用户不存在"));
                return;
            }
            if (teacher.getPassword() != null && teacher.getPassword().equals(password)) {
                req.getSession().setAttribute("role", "teacher");
                req.getSession().setAttribute("user", teacher);
                resp.getWriter().print(ApiResult.json(true, "登录成功"));
                return;
            }
            resp.getWriter().print(ApiResult.json(false, "密码错误，登录失败"));
            return;
        }
        resp.getWriter().print(ApiResult.json(false, "未知登录角色"));
    }
}
