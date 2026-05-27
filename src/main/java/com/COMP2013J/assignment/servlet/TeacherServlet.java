package com.COMP2013J.assignment.servlet;

import com.COMP2013J.assignment.entity.Teacher;
import com.COMP2013J.assignment.security.RoleHelper;
import com.COMP2013J.assignment.service.TeacherService;
import com.COMP2013J.assignment.utils.ApiResult;
import com.COMP2013J.assignment.utils.vo.PagerVO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/teacher")
public class TeacherServlet extends HttpServlet {
    private final TeacherService teacherService = new TeacherService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession(false);
        String r = req.getParameter("r");
        if ("add".equals(r)) {
            if (!RoleHelper.isAdmin(session)) {
                resp.sendRedirect(req.getContextPath() + "/teacher");
                return;
            }
            req.getRequestDispatcher("/WEB-INF/view/teacher-add.jsp").forward(req, resp);
            return;
        }
        if ("edit".equals(r)) {
            if (!RoleHelper.isAdmin(session)) {
                resp.sendRedirect(req.getContextPath() + "/teacher");
                return;
            }
            String tno = req.getParameter("tno");
            Teacher teacher = teacherService.getByTno(tno);
            if (teacher == null) {
                resp.sendRedirect(req.getContextPath() + "/teacher");
                return;
            }
            req.setAttribute("teacher", teacher);
            req.getRequestDispatcher("/WEB-INF/view/teacher-edit.jsp").forward(req, resp);
            return;
        }

        int current = parsePositiveInt(req.getParameter("current"), 1);
        int size = parsePositiveInt(req.getParameter("size"), 10);

        String tno = req.getParameter("tno");
        String tname = req.getParameter("tname");

        PagerVO<Teacher> pagerVO = new PagerVO<>();
        try {
            PagerVO<Teacher> result = teacherService.page(current, size, tno, tname);
            if (result != null) {
                pagerVO = result;
            } else {
                pagerVO.setCurrent(current);
                pagerVO.setSize(size);
                pagerVO.setTotal(0);
                pagerVO.setList(new ArrayList<>());
                req.setAttribute("errorMsg", "教师数据加载失败，请检查数据库连接配置。");
            }
        } catch (Exception e) {
            pagerVO.setCurrent(current);
            pagerVO.setSize(size);
            pagerVO.setTotal(0);
            pagerVO.setList(new ArrayList<>());
            req.setAttribute("errorMsg", "教师数据加载异常：" + e.getMessage());
        }
        pagerVO.init();

        req.setAttribute("tno", tno);
        req.setAttribute("tname", tname);
        req.setAttribute("size", pagerVO.getSize());
        req.setAttribute("pagerVO", pagerVO);
        req.setAttribute("canManageTeacher", RoleHelper.isAdmin(session));
        req.getRequestDispatcher("/WEB-INF/view/teacher-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        HttpSession session = req.getSession(false);
        if (!RoleHelper.isAdmin(session)) {
            resp.getWriter().write(ApiResult.json(false, "权限不足"));
            return;
        }
        String r = req.getParameter("r");

        if ("add".equals(r) || "edit".equals(r)) {
            Teacher teacher = new Teacher();
            teacher.setTno(req.getParameter("tno"));
            if ("add".equals(r)) {
                teacher.setPassword(req.getParameter("password"));
            }
            teacher.setTname(req.getParameter("tname"));

            String msg;
            if ("add".equals(r)) {
                msg = teacherService.insert(teacher);
            } else {
                msg = teacherService.update(teacher);
            }
            if (msg != null) {
                resp.getWriter().write(ApiResult.json(false, msg));
            } else {
                resp.getWriter().write(ApiResult.json(true, "保存成功"));
            }
            return;
        }

        if ("del".equals(r)) {
            String tno = req.getParameter("tno");
            String msg = teacherService.deleteWithCheck(tno);
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
