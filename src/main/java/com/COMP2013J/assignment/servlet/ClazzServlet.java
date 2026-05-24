package com.COMP2013J.assignment.servlet;

import com.COMP2013J.assignment.entity.Clazz;
import com.COMP2013J.assignment.security.RoleHelper;
import com.COMP2013J.assignment.utils.ApiResult;
import com.COMP2013J.assignment.utils.vo.PagerVO;
import com.COMP2013J.assignment.service.ClazzService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/clazz")
public class ClazzServlet extends HttpServlet {
    ClazzService clazzService = new ClazzService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession(false);
        boolean canManage = RoleHelper.isAdmin(session);
        req.setAttribute("canManageClazz", canManage);
        String r = req.getParameter("r");
        if ("add".equals(r) || "edit".equals(r)) {
            if (!canManage) {
                resp.sendRedirect(req.getContextPath() + "/clazz");
                return;
            }
        }
        if (r == null) {
            int current = parsePositiveInt(req.getParameter("current"), 1);
            int size = parsePositiveInt(req.getParameter("size"), 10);
            String clazzno = req.getParameter("clazzno");
            String name = req.getParameter("name");
            PagerVO<Clazz> pagerVO = new PagerVO<>();
            try {
                PagerVO<Clazz> result = clazzService.page(current, size, clazzno, name);
                if (result != null) {
                    pagerVO = result;
                } else {
                    pagerVO.setCurrent(current);
                    pagerVO.setSize(size);
                    pagerVO.setTotal(0);
                    pagerVO.setList(new ArrayList<>());
                    req.setAttribute("errorMsg", "班级数据加载失败，请检查数据库连接配置。");
                }
            } catch (Exception e) {
                pagerVO.setCurrent(current);
                pagerVO.setSize(size);
                pagerVO.setTotal(0);
                pagerVO.setList(new ArrayList<>());
                req.setAttribute("errorMsg", "班级数据加载异常，请稍后重试。");
            }
            pagerVO.init();

            req.setAttribute("clazzno", clazzno);
            req.setAttribute("name", name);
            req.setAttribute("size", pagerVO.getSize());
            req.setAttribute("pagerVO", pagerVO);
            req.getRequestDispatcher("/WEB-INF/view/clazz-list.jsp").forward(req, resp);
            return;
        }
        if ("add".equals(r)) {
            req.getRequestDispatcher("/WEB-INF/view/clazz-add.jsp").forward(req, resp);
            return;
        }
        if ("edit".equals(r)) {
            String clazzno = req.getParameter("clazzno");
            Clazz clazz = clazzService.getByClazzno(clazzno);
            if (clazz == null) {
                resp.sendRedirect(req.getContextPath() + "/clazz");
                return;
            }
            req.setAttribute("clazz", clazz);
            req.getRequestDispatcher("/WEB-INF/view/clazz-edit.jsp").forward(req, resp);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        HttpSession session = req.getSession(false);
        if (!RoleHelper.isAdmin(session)) {
            resp.getWriter().write(ApiResult.json(false, "仅管理员可维护班级信息"));
            return;
        }
        String r = req.getParameter("r");
        if ("add".equals(r) || "edit".equals(r)) {
            String clazzno = req.getParameter("clazzno");
            String name = req.getParameter("name");
            Clazz clazz = new Clazz();
            clazz.setName(name);
            clazz.setClazzno(clazzno);
            if ("add".equals(r)) {
                String msg = clazzService.insert(clazz);
                if (msg != null) {
                    resp.getWriter().write(ApiResult.json(false, msg));
                } else {
                    resp.getWriter().write(ApiResult.json(true, "保存成功"));
                }
                return;
            }
            String msg = clazzService.update(clazz);
            if (msg != null) {
                resp.getWriter().write(ApiResult.json(false, msg));
            } else {
                resp.getWriter().write(ApiResult.json(true, "保存成功"));
            }
            return;
        }
        if ("del".equals(r)) {
            String clazzno = req.getParameter("clazzno");
            String msg = clazzService.delete(clazzno);
            if (msg != null) {
                resp.getWriter().write(ApiResult.json(false, msg));
            } else {
                resp.getWriter().write(ApiResult.json(true, "删除成功"));
            }
        }
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
