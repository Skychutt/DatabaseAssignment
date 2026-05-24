package com.COMP2013J.assignment.service;

import com.COMP2013J.assignment.dao.NoticeDao;
import com.COMP2013J.assignment.entity.Notice;
import com.COMP2013J.assignment.utils.vo.PagerVO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class NoticeService {

    private final NoticeDao dao = new NoticeDao();

    public Notice getById(long id) {
        return dao.getById(id);
    }

    public PagerVO<Notice> page(int current, int size, String publisher, String keyword) {
        StringBuilder whereSql = new StringBuilder(" where 1=1 ");
        List<Object> params = new ArrayList<>();
        if (hasText(publisher)) {
            whereSql.append(" and publisher = ?");
            params.add(publisher.trim());
        }
        if (hasText(keyword)) {
            whereSql.append(" and content like ?");
            params.add("%" + keyword.trim() + "%");
        }
        return dao.page(current, size, whereSql.toString(), params.toArray());
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
        if (notice.getId() == null || notice.getId() <= 0) {
            return "公告编号无效";
        }
        if (!hasText(notice.getContent())) {
            return "公告内容不能为空";
        }
        if (dao.update(notice) <= 0) {
            return "保存失败";
        }
        return null;
    }

    public String delete(long id) {
        if (id <= 0) {
            return "公告编号无效";
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
