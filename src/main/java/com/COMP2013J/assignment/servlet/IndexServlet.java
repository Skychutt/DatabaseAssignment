package com.COMP2013J.assignment.servlet;

import com.COMP2013J.assignment.entity.Clazz;
import com.COMP2013J.assignment.service.ClazzService;
import com.COMP2013J.assignment.service.CourseService;
import com.COMP2013J.assignment.service.StudentService;
import com.COMP2013J.assignment.service.StuCourseService;
import com.COMP2013J.assignment.service.TeacherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * 首页的数据
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private final StudentService studentService = new StudentService();
    private final ClazzService clazzService = new ClazzService();
    private final TeacherService teacherService = new TeacherService();
    private final CourseService courseService = new CourseService();
    private final StuCourseService stuCourseService = new StuCourseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        try {
            int teacherCount = teacherService.count();
            int courseCount = courseService.count();
            int clazzCount = clazzService.count();
            int studentCount = studentService.count();
            int enrollmentCount = stuCourseService.countAllEnrollments();
            List<Clazz> clazzes = clazzService.statistics();
            if (clazzes == null) {
                clazzes = Collections.emptyList();
            }
            String body = "{\"success\":true,\"message\":\"成功\",\"data\":{" +
                    "\"clazzCount\":" + clazzCount + "," +
                    "\"studentCount\":" + studentCount + "," +
                    "\"teacherCount\":" + teacherCount + "," +
                    "\"courseCount\":" + courseCount + "," +
                    "\"enrollmentCount\":" + enrollmentCount + "," +
                    "\"clazzes\":" + toClazzesJson(clazzes) +
                    "}}";
            resp.getWriter().write(body);
        } catch (Throwable e) {
            String msg = e.getMessage() == null ? "服务器异常" : escapeJson(e.getMessage());
            resp.getWriter().write("{\"success\":false,\"message\":\"" + msg + "\",\"data\":null}");
        }
    }

    private String toClazzesJson(List<Clazz> clazzes) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < clazzes.size(); i++) {
            Clazz c = clazzes.get(i);
            if (i > 0) {
                sb.append(",");
            }
            String clazzno = c.getClazzno() == null ? "" : escapeJson(c.getClazzno());
            String name = c.getName() == null ? "" : escapeJson(c.getName());
            int stuCount = c.getStuCount();
            sb.append("{\"clazzno\":\"")
                    .append(clazzno)
                    .append("\",\"name\":\"")
                    .append(name)
                    .append("\",\"stuCount\":")
                    .append(stuCount)
                    .append("}");
        }
        sb.append("]");
        return sb.toString();
    }

    private String escapeJson(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "\\r")
                .replace("\n", "\\n");
    }
}
