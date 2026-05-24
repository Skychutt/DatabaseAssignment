package com.COMP2013J.assignment.servlet;

import com.COMP2013J.assignment.entity.Teacher;
import com.COMP2013J.assignment.security.RoleHelper;
import com.COMP2013J.assignment.service.AdminService;
import com.COMP2013J.assignment.service.TeacherService;
import com.COMP2013J.assignment.utils.vo.PagerVO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet("/directory")
public class DirectoryServlet extends HttpServlet {

    private final AdminService adminService = new AdminService();
    private final TeacherService teacherService = new TeacherService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!RoleHelper.isAdmin(req.getSession(false)) && !RoleHelper.isTeacher(req.getSession(false))) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "权限不足");
            return;
        }
        req.setAttribute("adminNames", adminService.listAllUsernames());
        PagerVO<Teacher> p = teacherService.page(1, 500, "", "");
        List<Teacher> list = p != null ? p.getList() : null;
        req.setAttribute("teachers", list != null ? list : Collections.emptyList());
        req.getRequestDispatcher("/WEB-INF/view/directory.jsp").forward(req, resp);
    }
}
