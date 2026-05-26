package com.COMP2013J.assignment.servlet;

import com.COMP2013J.assignment.entity.Course;
import com.COMP2013J.assignment.entity.Student;
import com.COMP2013J.assignment.entity.Teacher;
import com.COMP2013J.assignment.security.RoleHelper;
import com.COMP2013J.assignment.service.StuCourseService;
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

@WebServlet("/stuCourse")
public class StuCourseServlet extends HttpServlet {

    private final StuCourseService stuCourseService = new StuCourseService();
    private final TeacherService teacherService = new TeacherService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession(false);
        if (RoleHelper.isStudent(session)) {
            Student st = (Student) session.getAttribute("user");
            if (st == null) {
                resp.sendRedirect(req.getContextPath() + "/login.jsp");
                return;
            }
            req.setAttribute("myRows", stuCourseService.listMyEnrollments(st.getSno()));
            int availCurrent = parsePositiveInt(req.getParameter("availCurrent"), 1);
            int availSize = parsePositiveInt(req.getParameter("availSize"), 8);
            PagerVO<Course> avail = stuCourseService.pageChoosable(st.getSno(), availCurrent, availSize);
            if (avail == null) {
                avail = new PagerVO<>();
                avail.setCurrent(1);
                avail.setSize(availSize);
                avail.setTotal(0);
                avail.setList(java.util.Collections.emptyList());
                avail.init();
            }
            req.setAttribute("availPager", avail);
            req.setAttribute("teacherNameMap", teacherService.getTnoNameMap());
            req.getRequestDispatcher("/WEB-INF/view/stu-course-student.jsp").forward(req, resp);
            return;
        }
        if (RoleHelper.isTeacher(session)) {
            Teacher t = (Teacher) session.getAttribute("user");
            if (t == null) {
                resp.sendRedirect(req.getContextPath() + "/login.jsp");
                return;
            }
            req.setAttribute("gradeRows", stuCourseService.listForTeacher(t.getTno()));
            req.getRequestDispatcher("/WEB-INF/view/stu-course-teacher.jsp").forward(req, resp);
            return;
        }
        if (RoleHelper.isAdmin(session)) {
            req.setAttribute("allRows", stuCourseService.listAllForAdmin());
            req.getRequestDispatcher("/WEB-INF/view/stu-course-admin.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        HttpSession session = req.getSession(false);
        String r = req.getParameter("r");

        if ("choose".equals(r)) {
            if (!RoleHelper.isStudent(session)) {
                resp.getWriter().write(ApiResult.json(false, "权限不足"));
                return;
            }
            Student st = (Student) session.getAttribute("user");
            String cno = req.getParameter("cno");
            String msg = stuCourseService.choose(st.getSno(), cno);
            if (msg != null) {
                resp.getWriter().write(ApiResult.json(false, msg));
            } else {
                resp.getWriter().write(ApiResult.json(true, "选课成功"));
            }
            return;
        }

        if ("drop".equals(r)) {
            if (!RoleHelper.isStudent(session)) {
                resp.getWriter().write(ApiResult.json(false, "权限不足"));
                return;
            }
            Student st = (Student) session.getAttribute("user");
            String cno = req.getParameter("cno");
            String msg = stuCourseService.drop(st.getSno(), cno);
            if (msg != null) {
                resp.getWriter().write(ApiResult.json(false, msg));
            } else {
                resp.getWriter().write(ApiResult.json(true, "退选成功"));
            }
            return;
        }

        if ("grade".equals(r)) {
            boolean admin = RoleHelper.isAdmin(session);
            String tno = null;
            if (RoleHelper.isTeacher(session)) {
                Teacher t = (Teacher) session.getAttribute("user");
                tno = t == null ? null : t.getTno();
            }
            if (!admin && tno == null) {
                resp.getWriter().write(ApiResult.json(false, "权限不足"));
                return;
            }
            String cno = req.getParameter("cno");
            String sno = req.getParameter("sno");
            String scoreStr = req.getParameter("score");
            String evaluation = req.getParameter("evaluation");
            Double score = null;
            if (scoreStr != null && !scoreStr.trim().isEmpty()) {
                try {
                    score = Double.parseDouble(scoreStr.trim());
                } catch (NumberFormatException e) {
                    resp.getWriter().write(ApiResult.json(false, "分数格式错误"));
                    return;
                }
            }
            String msg = stuCourseService.grade(admin, tno, cno, sno, score, evaluation);
            if (msg != null) {
                resp.getWriter().write(ApiResult.json(false, msg));
            } else {
                resp.getWriter().write(ApiResult.json(true, "保存成功"));
            }
            return;
        }

        if ("adminDrop".equals(r)) {
            if (!RoleHelper.isAdmin(session)) {
                resp.getWriter().write(ApiResult.json(false, "权限不足"));
                return;
            }
            String cno = req.getParameter("cno");
            String sno = req.getParameter("sno");
            String msg = stuCourseService.adminRemoveEnrollment(cno, sno);
            if (msg != null) {
                resp.getWriter().write(ApiResult.json(false, msg));
            } else {
                resp.getWriter().write(ApiResult.json(true, "已移除选课记录"));
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
            int p = Integer.parseInt(value.trim());
            return p > 0 ? p : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
