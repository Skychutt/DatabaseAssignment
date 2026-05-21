package com.COMP2013J.assignment.servlet;

import com.COMP2013J.assignment.entity.Student;
import com.COMP2013J.assignment.entity.StudentArchive;
import com.COMP2013J.assignment.security.RoleHelper;
import com.COMP2013J.assignment.service.StudentArchiveService;
import com.COMP2013J.assignment.utils.ApiResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/studentArchive")
public class StudentArchiveServlet extends HttpServlet {

    private final StudentArchiveService archiveService = new StudentArchiveService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String loginSno = requireStudentSno(req, resp);
        if (loginSno == null) {
            return;
        }

        String r = req.getParameter("r");
        if ("add".equals(r)) {
            if (archiveService.getBySno(loginSno) != null) {
                resp.sendRedirect(req.getContextPath() + "/studentArchive");
                return;
            }
            Student user = (Student) req.getSession(false).getAttribute("user");
            req.setAttribute("formMode", "add");
            req.setAttribute("prefillSno", loginSno);
            req.setAttribute("prefillClazzno", user == null ? "" : user.getClazzno());
            req.getRequestDispatcher("/WEB-INF/view/student-archive-form.jsp").forward(req, resp);
            return;
        }

        if ("edit".equals(r)) {
            StudentArchive archive = archiveService.getBySno(loginSno);
            if (archive == null) {
                resp.sendRedirect(req.getContextPath() + "/studentArchive?r=add");
                return;
            }
            req.setAttribute("formMode", "edit");
            req.setAttribute("archive", archive);
            req.getRequestDispatcher("/WEB-INF/view/student-archive-form.jsp").forward(req, resp);
            return;
        }

        StudentArchive archive = archiveService.getBySno(loginSno);
        req.setAttribute("archive", archive);
        req.setAttribute("hasArchive", archive != null);
        req.getRequestDispatcher("/WEB-INF/view/student-archive.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        HttpSession session = req.getSession(false);
        if (!RoleHelper.isStudent(session)) {
            resp.getWriter().write(ApiResult.json(false, "仅学生可管理个人档案"));
            return;
        }
        String loginSno = requireStudentSno(req, resp);
        if (loginSno == null) {
            resp.getWriter().write(ApiResult.json(false, "请先登录"));
            return;
        }

        String r = req.getParameter("r");
        StudentArchive archive = bindArchive(req);

        if ("add".equals(r)) {
            String msg = archiveService.insert(archive, loginSno);
            if (msg != null) {
                resp.getWriter().write(ApiResult.json(false, msg));
            } else {
                resp.getWriter().write(ApiResult.json(true, "档案创建成功"));
            }
            return;
        }

        if ("edit".equals(r)) {
            archive.setArchiveId(req.getParameter("archiveId"));
            String msg = archiveService.update(archive, loginSno);
            if (msg != null) {
                resp.getWriter().write(ApiResult.json(false, msg));
            } else {
                resp.getWriter().write(ApiResult.json(true, "保存成功"));
            }
            return;
        }

        if ("del".equals(r)) {
            String msg = archiveService.delete(req.getParameter("archiveId"), loginSno);
            if (msg != null) {
                resp.getWriter().write(ApiResult.json(false, msg));
            } else {
                resp.getWriter().write(ApiResult.json(true, "删除成功"));
            }
            return;
        }

        resp.getWriter().write(ApiResult.json(false, "未知请求"));
    }

    private StudentArchive bindArchive(HttpServletRequest req) {
        StudentArchive archive = new StudentArchive();
        archive.setIdCard(req.getParameter("idCard"));
        archive.setNationality(req.getParameter("nationality"));
        archive.setBirthPlace(req.getParameter("birthPlace"));
        archive.setPoliticalStatus(req.getParameter("politicalStatus"));
        archive.setEnrollmentType(req.getParameter("enrollmentType"));
        archive.setMajor(req.getParameter("major"));
        archive.setClazzno(req.getParameter("clazzno"));
        archive.setGraduationSchool(req.getParameter("graduationSchool"));
        archive.setGuardianName(req.getParameter("guardianName"));
        archive.setGuardianPhone(req.getParameter("guardianPhone"));
        String status = req.getParameter("archiveStatus");
        if (status != null && !status.trim().isEmpty()) {
            try {
                archive.setArchiveStatus(Integer.parseInt(status.trim()));
            } catch (NumberFormatException ignored) {
            }
        }
        return archive;
    }

    private String requireStudentSno(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (!RoleHelper.isStudent(session)) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return null;
        }
        Student user = (Student) session.getAttribute("user");
        if (user == null || user.getSno() == null || user.getSno().trim().isEmpty()) {
            if ("POST".equalsIgnoreCase(req.getMethod())) {
                return null;
            }
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return null;
        }
        return user.getSno().trim();
    }
}
