package com.COMP2013J.assignment.servlet;

import com.COMP2013J.assignment.entity.Course;
import com.COMP2013J.assignment.entity.Teacher;
import com.COMP2013J.assignment.security.CourseAccessHelper;
import com.COMP2013J.assignment.security.RoleHelper;
import com.COMP2013J.assignment.service.CourseService;
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

@WebServlet("/course")
public class CourseServlet extends HttpServlet {

    private final CourseService courseService = new CourseService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession(false);
        String r = req.getParameter("r");
        if ("add".equals(r) || "edit".equals(r)) {
            if (!CourseAccessHelper.canManageCourse(session)) {
                resp.sendRedirect(req.getContextPath() + "/course");
                return;
            }
        }
        if ("add".equals(r)) {
            req.setAttribute("courseAdminMode", RoleHelper.isAdmin(session));
            req.getRequestDispatcher("/WEB-INF/view/course-add.jsp").forward(req, resp);
            return;
        }
        if ("edit".equals(r)) {
            String cno = req.getParameter("cno");
            Course course = courseService.getByCno(cno);
            if (course == null || !CourseAccessHelper.isCourseOwner(session, course)) {
                resp.sendRedirect(req.getContextPath() + "/course");
                return;
            }
            req.setAttribute("course", course);
            req.setAttribute("courseAdminMode", RoleHelper.isAdmin(session));
            req.getRequestDispatcher("/WEB-INF/view/course-edit.jsp").forward(req, resp);
            return;
        }

        int current = parsePositiveInt(req.getParameter("current"), 1);
        int size = parsePositiveInt(req.getParameter("size"), 10);
        String cno = req.getParameter("cno");
        String cname = req.getParameter("cname");
        String tno = req.getParameter("tno");

        PagerVO<Course> pagerVO = new PagerVO<>();
        try {
            PagerVO<Course> result = courseService.page(current, size, cno, cname, tno);
            if (result != null) {
                pagerVO = result;
            } else {
                pagerVO.setCurrent(current);
                pagerVO.setSize(size);
                pagerVO.setTotal(0);
                pagerVO.setList(new ArrayList<>());
                req.setAttribute("errorMsg", "课程数据加载失败，请检查数据库连接配置。");
            }
        } catch (Exception e) {
            pagerVO.setCurrent(current);
            pagerVO.setSize(size);
            pagerVO.setTotal(0);
            pagerVO.setList(new ArrayList<>());
            req.setAttribute("errorMsg", "课程数据加载异常，请稍后重试。");
        }
        pagerVO.init();

        req.setAttribute("cno", cno);
        req.setAttribute("cname", cname);
        req.setAttribute("tno", tno);
        req.setAttribute("size", pagerVO.getSize());
        req.setAttribute("pagerVO", pagerVO);
        req.setAttribute("canManageCourse", CourseAccessHelper.canManageCourse(session));
        req.getRequestDispatcher("/WEB-INF/view/course-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        HttpSession session = req.getSession(false);
        if (!CourseAccessHelper.canManageCourse(session)) {
            resp.getWriter().write(ApiResult.json(false, "权限不足"));
            return;
        }

        boolean adminOperator = RoleHelper.isAdmin(session);
        String operatorTno = resolveOperatorTno(session);
        String r = req.getParameter("r");

        if ("add".equals(r) || "edit".equals(r)) {
            Course course = new Course();
            course.setCno(req.getParameter("cno"));
            course.setTno(req.getParameter("tno"));
            course.setCname(req.getParameter("cname"));
            course.setContent(req.getParameter("content"));

            String begindate = req.getParameter("begindate");
            if (begindate != null && !begindate.trim().isEmpty()) {
                try {
                    course.setBegindate(Date.valueOf(begindate.trim()));
                } catch (Exception e) {
                    resp.getWriter().write(ApiResult.json(false, "开始日期格式错误"));
                    return;
                }
            }
            String enddate = req.getParameter("enddate");
            if (enddate != null && !enddate.trim().isEmpty()) {
                try {
                    course.setEnddate(Date.valueOf(enddate.trim()));
                } catch (Exception e) {
                    resp.getWriter().write(ApiResult.json(false, "结束日期格式错误"));
                    return;
                }
            }

            String credits = req.getParameter("credits");
            if (credits != null && !credits.trim().isEmpty()) {
                try {
                    course.setCredits(Double.parseDouble(credits.trim()));
                } catch (NumberFormatException e) {
                    resp.getWriter().write(ApiResult.json(false, "学分格式错误"));
                    return;
                }
            }

            String maximum = req.getParameter("maximum");
            if (maximum != null && !maximum.trim().isEmpty()) {
                try {
                    course.setMaximum(Integer.parseInt(maximum.trim()));
                } catch (NumberFormatException e) {
                    resp.getWriter().write(ApiResult.json(false, "人数上限格式错误"));
                    return;
                }
            }

            if (adminOperator) {
                String countStr = req.getParameter("count");
                if (countStr != null && !countStr.trim().isEmpty()) {
                    try {
                        course.setCount(Integer.parseInt(countStr.trim()));
                    } catch (NumberFormatException e) {
                        resp.getWriter().write(ApiResult.json(false, "已选人数格式错误"));
                        return;
                    }
                }
            }

            String msg;
            if ("add".equals(r)) {
                msg = courseService.insert(course, adminOperator, operatorTno);
            } else {
                msg = courseService.update(course, adminOperator, operatorTno);
            }
            if (msg != null) {
                resp.getWriter().write(ApiResult.json(false, msg));
            } else {
                resp.getWriter().write(ApiResult.json(true, "保存成功"));
            }
            return;
        }

        if ("del".equals(r)) {
            String cno = req.getParameter("cno");
            String msg = courseService.deleteWithCheck(cno, adminOperator, operatorTno);
            if (msg != null) {
                resp.getWriter().write(ApiResult.json(false, msg));
            } else {
                resp.getWriter().write(ApiResult.json(true, "删除成功"));
            }
            return;
        }

        resp.getWriter().write(ApiResult.json(false, "未知请求"));
    }

    private String resolveOperatorTno(HttpSession session) {
        if (!RoleHelper.isTeacher(session)) {
            return null;
        }
        Object user = session.getAttribute("user");
        if (user instanceof Teacher) {
            return ((Teacher) user).getTno();
        }
        return null;
    }

    private int parsePositiveInt(String value, int defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            int parsed = Integer.parseInt(value.trim());
            return parsed > 0 ? parsed : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
