package com.COMP2013J.assignment.service;

import com.COMP2013J.assignment.dao.CourseDao;
import com.COMP2013J.assignment.dao.TeacherDao;
import com.COMP2013J.assignment.entity.Teacher;
import com.COMP2013J.assignment.security.PasswordUtil;
import com.COMP2013J.assignment.utils.vo.PagerVO;

import java.util.ArrayList;
import java.util.List;

public class TeacherService {

    private final TeacherDao dao = new TeacherDao();
    private final CourseDao courseDao = new CourseDao();

    public Teacher getByTno(String tno) {
        return dao.getByTno(tno);
    }

    public int count() {
        return dao.count();
    }

    public PagerVO<Teacher> page(int current, int size, String tno, String tname) {
        StringBuilder whereSql = new StringBuilder(" where 1=1 ");
        List<Object> params = new ArrayList<>();
        if (hasText(tname)) {
            whereSql.append(" and tname like ?");
            params.add("%" + tname.trim() + "%");
        }
        if (hasText(tno)) {
            whereSql.append(" and tno = ?");
            params.add(tno.trim());
        }
        return dao.page(current, size, whereSql.toString(), params.toArray());
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public void delete(String tno) {
        dao.delete(tno);
    }

    public String insert(Teacher teacher) {
        if (!hasText(teacher.getTno())) {
            return "工号不可为空";
        }
        if (!hasText(teacher.getPassword())) {
            return "密码不可为空";
        }
        if (dao.getByTno(teacher.getTno().trim()) != null) {
            return "工号已存在！";
        }
        teacher.setPassword(PasswordUtil.hash(teacher.getPassword()));
        dao.insert(teacher);
        return null;
    }

    public String update(Teacher teacher) {
        if (!hasText(teacher.getTno())) {
            return "工号不可为空";
        }
        if (teacher.getPassword() != null && teacher.getPassword().trim().isEmpty()) {
            teacher.setPassword(null);
        } else if (teacher.getPassword() != null) {
            teacher.setPassword(PasswordUtil.hash(teacher.getPassword()));
        }
        dao.update(teacher);
        return null;
    }

    public String deleteWithCheck(String tno) {
        if (!hasText(tno)) {
            return "工号不可为空";
        }
        if (courseDao.countByTno(tno.trim()) > 0) {
            return "该教师仍有关联课程，无法删除。";
        }
        dao.delete(tno.trim());
        return null;
    }
}
