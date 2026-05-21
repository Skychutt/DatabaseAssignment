package com.COMP2013J.assignment.service;

import com.COMP2013J.assignment.dao.NoticeDao;
import com.COMP2013J.assignment.entity.Notice;
import com.COMP2013J.assignment.utils.vo.PagerVO;

import java.sql.Date;

public class NoticeService {

    private final NoticeDao dao = new NoticeDao();

    public Notice getById(long id) {
        return dao.getById(id);
    }

    public PagerVO<Notice> page(int current, int size, String publisher, String keyword) {
        String whereSql = " where 1=1 ";
        if (hasText(publisher)) {
            whereSql += " and publisher = '" + publisher.trim().replace("'", "''") + "'";
        }
        if (hasText(keyword)) {
            whereSql += " and content like '%" + keyword.trim().replace("'", "''") + "%'";
        }
        return dao.page(current, size, whereSql);
    }

    public String insert(Notice notice) {
        if (!hasText(notice.getPublisher())) {
            return "发布人不能为空";
        }
        if (!hasText(notice.getContent())) {
            return "公告内容不能为空";
        }
        if (notice.getCreateTime() == null) {
            notice.setCreateTime(new Date(System.currentTimeMillis()));
        }
        if (dao.insert(notice) <= 0) {
            return "发布失败";
        }
        return null;
    }

    public String update(Notice notice) {
        if (notice.getId() == null) {
            return "公告编号无效";
        }
        if (!hasText(notice.getContent())) {
            return "公告内容不能为空";
        }
        Notice existing = dao.getById(notice.getId());
        if (existing == null) {
            return "公告不存在";
        }
        if (notice.getCreateTime() == null) {
            notice.setCreateTime(existing.getCreateTime());
        }
        notice.setPublisher(existing.getPublisher());
        if (dao.update(notice) <= 0) {
            return "保存失败";
        }
        return null;
    }

    public String delete(long id) {
        if (id <= 0) {
            return "公告编号无效";
        }
        if (dao.getById(id) == null) {
            return "公告不存在";
        }
        if (dao.delete(id) <= 0) {
            return "删除失败";
        }
        return null;
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
