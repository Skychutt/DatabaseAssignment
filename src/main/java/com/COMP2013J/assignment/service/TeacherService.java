package com.COMP2013J.assignment.service;

import com.COMP2013J.assignment.dao.CourseDao;
import com.COMP2013J.assignment.dao.TeacherDao;
import com.COMP2013J.assignment.entity.Teacher;
import com.COMP2013J.assignment.utils.vo.PagerVO;

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
        String whereSql = " where 1=1 ";
        if (hasText(tname)) {
            whereSql += " and tname like '%" + tname.trim() + "%'";
        }
        if (hasText(tno)) {
            whereSql += " and tno = '" + tno.trim() + "'";
        }
        return dao.page(current, size, whereSql);
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public void delete(String tno) {
        dao.delete(tno);
    }

    public String deleteWithCheck(String tno) {
        if (tno == null || tno.trim().isEmpty()) {
            return "工号不可为空";
        }
        if (courseDao.countByTno(tno.trim()) > 0) {
            return "该教师仍有关联课程，无法删除。";
        }
        dao.delete(tno.trim());
        return null;
    }

    public String insert(Teacher teacher) {
        if (teacher.getTno() == null || teacher.getTno().isEmpty()) {
            return "教师工号不可为空！";
        }
        if (teacher.getPassword() == null || teacher.getPassword().isEmpty()) {
            return "密码不可为空！";
        }
        if (teacher.getTname() == null || teacher.getTname().isEmpty()) {
            return "姓名不可为空！";
        }
        if (dao.getByTno(teacher.getTno()) != null) {
            return "工号已存在！";
        }
        dao.insert(teacher);
        return null;
    }

    public String update(Teacher teacher) {
        if (teacher.getTno() == null || teacher.getTno().isEmpty()) {
            return "被修改教师工号不可为空！";
        }
        dao.update(teacher);
        return null;
    }
}
