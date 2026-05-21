package com.COMP2013J.assignment.servlet;

import com.COMP2013J.assignment.entity.Admin;
import com.COMP2013J.assignment.entity.Notice;
import com.COMP2013J.assignment.security.RoleHelper;
import com.COMP2013J.assignment.service.NoticeService;
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

@WebServlet("/notice")
public class NoticeServlet extends HttpServlet {

    private final NoticeService noticeService = new NoticeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession(false);
        String r = req.getParameter("r");

        if ("add".equals(r) || "edit".equals(r)) {
            if (!RoleHelper.isAdmin(session)) {
                resp.sendRedirect(req.getContextPath() + "/notice");
                return;
            }
        }
        if ("add".equals(r)) {
            req.getRequestDispatcher("/WEB-INF/view/notice-add.jsp").forward(req, resp);
            return;
        }
        if ("edit".equals(r)) {
            long id = parseLong(req.getParameter("id"), 0);
            Notice notice = id > 0 ? noticeService.getById(id) : null;
            if (notice == null) {
                resp.sendRedirect(req.getContextPath() + "/notice");
                return;
            }
            req.setAttribute("notice", notice);
            req.getRequestDispatcher("/WEB-INF/view/notice-edit.jsp").forward(req, resp);
            return;
        }

        int current = parsePositiveInt(req.getParameter("current"), 1);
        int size = parsePositiveInt(req.getParameter("size"), 10);
        String publisher = req.getParameter("publisher");
        String keyword = req.getParameter("keyword");

        PagerVO<Notice> pagerVO = new PagerVO<>();
        try {
            PagerVO<Notice> result = noticeService.page(current, size, publisher, keyword);
            if (result != null) {
                pagerVO = result;
            } else {
                pagerVO.setCurrent(current);
                pagerVO.setSize(size);
                pagerVO.setTotal(0);
                pagerVO.setList(new ArrayList<>());
                req.setAttribute("errorMsg", "公告数据加载失败，请检查数据库连接配置。");
            }
        } catch (Exception e) {
            pagerVO.setCurrent(current);
            pagerVO.setSize(size);
            pagerVO.setTotal(0);
            pagerVO.setList(new ArrayList<>());
            req.setAttribute("errorMsg", "公告数据加载异常：" + e.getMessage());
        }
        pagerVO.init();

        req.setAttribute("publisher", publisher);
        req.setAttribute("keyword", keyword);
        req.setAttribute("size", pagerVO.getSize());
        req.setAttribute("pagerVO", pagerVO);
        req.setAttribute("canManageNotice", RoleHelper.isAdmin(session));
        req.getRequestDispatcher("/WEB-INF/view/notice-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        HttpSession session = req.getSession(false);
        if (!RoleHelper.isAdmin(session)) {
            resp.getWriter().write(ApiResult.json(false, "权限不足，仅管理员可发布公告"));
            return;
        }

        String r = req.getParameter("r");
        if ("add".equals(r) || "edit".equals(r)) {
            Notice notice = new Notice();
            if ("edit".equals(r)) {
                notice.setId(parseLong(req.getParameter("id"), 0));
                if (notice.getId() == null || notice.getId() <= 0) {
                    resp.getWriter().write(ApiResult.json(false, "公告编号无效"));
                    return;
                }
            } else {
                Object user = session.getAttribute("user");
                if (!(user instanceof Admin)) {
                    resp.getWriter().write(ApiResult.json(false, "无法获取当前管理员账号"));
                    return;
                }
                notice.setPublisher(((Admin) user).getUsername());
            }

            String createTimeStr = req.getParameter("createTime");
            if (createTimeStr != null && !createTimeStr.trim().isEmpty()) {
                try {
                    notice.setCreateTime(Date.valueOf(createTimeStr.trim()));
                } catch (Exception e) {
                    resp.getWriter().write(ApiResult.json(false, "发布日期格式错误"));
                    return;
                }
            }

            notice.setContent(req.getParameter("content"));

            String msg = "add".equals(r) ? noticeService.insert(notice) : noticeService.update(notice);
            if (msg != null) {
                resp.getWriter().write(ApiResult.json(false, msg));
            } else {
                resp.getWriter().write(ApiResult.json(true, "保存成功"));
            }
            return;
        }

        if ("del".equals(r)) {
            long id = parseLong(req.getParameter("id"), 0);
            String msg = noticeService.delete(id);
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
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private long parseLong(String value, long defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
