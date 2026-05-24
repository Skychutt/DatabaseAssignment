package com.COMP2013J.assignment.servlet;

import com.COMP2013J.assignment.entity.Student;
import com.COMP2013J.assignment.security.RoleHelper;
import com.COMP2013J.assignment.service.StudentService;
import com.COMP2013J.assignment.utils.ApiResult;
import com.COMP2013J.assignment.utils.vo.PagerVO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {
    StudentService studentService = new StudentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession(false);
        boolean canManage = RoleHelper.isAdmin(session) || RoleHelper.isTeacher(session);
        req.setAttribute("canManageStudent", canManage);
        String r = req.getParameter("r");
        if ("add".equals(r) || "edit".equals(r)) {
            if (!canManage) {
                resp.sendRedirect(req.getContextPath() + "/student");
                return;
            }
        }
        if ("add".equals(r)) {
            req.getRequestDispatcher("/WEB-INF/view/student-add.jsp").forward(req, resp);
            return;
        }
        if ("edit".equals(r)) {
            String sno = req.getParameter("sno");
            Student student = studentService.getBySno(sno);
            if (student == null) {
                resp.sendRedirect(req.getContextPath() + "/student");
                return;
            }
            req.setAttribute("student", student);
            req.getRequestDispatcher("/WEB-INF/view/student-edit.jsp").forward(req, resp);
            return;
        }

        int current = parsePositiveInt(req.getParameter("current"), 1);
        int size = parsePositiveInt(req.getParameter("size"), 10);

        String sno = req.getParameter("sno");
        String name = req.getParameter("name");
        String gender = req.getParameter("gender");
        String clazzno = req.getParameter("clazzno");

        PagerVO<Student> pagerVO = new PagerVO<>();
        try {
            PagerVO<Student> result = studentService.page(current, size, sno, name, gender, clazzno);
            if (result != null) {
                pagerVO = result;
            } else {
                pagerVO.setCurrent(current);
                pagerVO.setSize(size);
                pagerVO.setTotal(0);
                pagerVO.setList(new ArrayList<>());
                req.setAttribute("errorMsg", "学生数据加载失败，请检查数据库连接配置。");
            }
        } catch (Exception e) {
            pagerVO.setCurrent(current);
            pagerVO.setSize(size);
            pagerVO.setTotal(0);
            pagerVO.setList(new ArrayList<>());
            req.setAttribute("errorMsg", "学生数据加载异常，请稍后重试。");
        }
        pagerVO.init();

        req.setAttribute("sno", sno);
        req.setAttribute("name", name);
        req.setAttribute("gender", gender);
        req.setAttribute("clazzno", clazzno);
        req.setAttribute("size", pagerVO.getSize());
        req.setAttribute("pagerVO", pagerVO);
        req.getRequestDispatcher("/WEB-INF/view/student-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        HttpSession session = req.getSession(false);
        if (!RoleHelper.isAdmin(session) && !RoleHelper.isTeacher(session)) {
            resp.getWriter().write(ApiResult.json(false, "权限不足"));
            return;
        }
        String r = req.getParameter("r");

        if ("add".equals(r) || "edit".equals(r)) {
            Student student = new Student();
            student.setSno(req.getParameter("sno"));
            student.setPassword(req.getParameter("password"));
            student.setName(req.getParameter("name"));
            student.setTele(req.getParameter("tele"));
            student.setGender(req.getParameter("gender"));
            student.setAddress(req.getParameter("address"));
            student.setClazzno(req.getParameter("clazzno"));

            String age = req.getParameter("age");
            if (age != null && !age.trim().isEmpty()) {
                try {
                    student.setAge(Integer.parseInt(age.trim()));
                } catch (NumberFormatException ignored) {
                    resp.getWriter().write(ApiResult.json(false, "年龄格式错误"));
                    return;
                }
            }
            String enterdate = req.getParameter("enterdate");
            if (enterdate != null && !enterdate.trim().isEmpty()) {
                try {
                    student.setEnterdate(Date.valueOf(enterdate.trim()));
                } catch (Exception ignored) {
                    resp.getWriter().write(ApiResult.json(false, "入学日期格式错误"));
                    return;
                }
            }

            String msg;
            if ("add".equals(r)) {
                msg = studentService.insert(student);
            } else {
                msg = studentService.update(student);
            }
            if (msg != null) {
                resp.getWriter().write(ApiResult.json(false, msg));
            } else {
                resp.getWriter().write(ApiResult.json(true, "保存成功"));
            }
            return;
        }

        if ("del".equals(r)) {
            String sno = req.getParameter("sno");
            String msg = studentService.deleteWithCheck(sno);
            if (msg != null) {
                resp.getWriter().write(ApiResult.json(false, msg));
            } else {
                resp.getWriter().write(ApiResult.json(true, "删除成功"));
            }
            return;
        }

        resp.getWriter().write(ApiResult.json(false, "未知请求"));
    }

    private int parsePositiveInt(String value, int defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            int parsed = Integer.parseInt(value.trim());
            return parsed > 0 ? parsed : defaultValue;
        } catch (NumberFormatException ignored) {
            return defaultValue;
        }
    }
}
