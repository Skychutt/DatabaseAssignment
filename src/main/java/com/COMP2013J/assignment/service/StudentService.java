package com.COMP2013J.assignment.service;

import com.COMP2013J.assignment.dao.ClazzDao;
import com.COMP2013J.assignment.dao.StudentDao;
import com.COMP2013J.assignment.entity.Student;
import com.COMP2013J.assignment.security.PasswordUtil;
import com.COMP2013J.assignment.utils.vo.PagerVO;

import java.util.ArrayList;
import java.util.List;

public class StudentService {

    StudentDao dao = new StudentDao();
    ClazzDao clazzDao = new ClazzDao();

    public Student getBySno(String sno) {
        return dao.getBySno(sno);
    }

    public int count() {
        return dao.count();
    }

    public PagerVO<Student> page(int current, int size, String sno, String name, String gender, String clazzno) {
        StringBuilder whereSql = new StringBuilder(" where 1=1 ");
        List<Object> params = new ArrayList<>();
        if (hasText(name)) {
            whereSql.append(" and name like ?");
            params.add("%" + name.trim() + "%");
        }
        if (hasText(sno)) {
            whereSql.append(" and sno = ?");
            params.add(sno.trim());
        }
        if (hasText(gender)) {
            whereSql.append(" and gender = ?");
            params.add(gender.trim());
        }
        if (hasText(clazzno)) {
            whereSql.append(" and clazzno = ?");
            params.add(clazzno.trim());
        }
        return dao.page(current, size, whereSql.toString(), params.toArray());
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public String deleteWithCheck(String sno) {
        if (!hasText(sno)) {
            return "学号不可为空";
        }
        String trimmed = sno.trim();
        if (dao.getBySno(trimmed) == null) {
            return "学生不存在";
        }
        int enrollments = dao.countStuCourseBySno(trimmed);
        if (enrollments > 0) {
            return "删除失败：该学生已有 " + enrollments + " 条选课记录，请先退选后再删除。";
        }
        if (dao.countArchiveBySno(trimmed) > 0) {
            return "删除失败：该学生仍有个人档案，请先删除档案后再删除学生。";
        }
        int rows = dao.delete(trimmed);
        if (rows <= 0) {
            return "删除失败，请稍后重试。";
        }
        return null;
    }

    public String insert(Student student) {
        if (student.getSno() == null || student.getSno().equals("")) {
            return "学生学号不可为空！";
        }
        if (student.getPassword() == null || student.getPassword().equals("")) {
            return "密码不可为空！";
        }
        if (student.getName() == null || student.getName().equals("")) {
            return "姓名不可为空！";
        }
        if (student.getClazzno() == null || student.getClazzno().equals("")) {
            return "班级不可为空！";
        }
        if (clazzDao.getByClazzno(student.getClazzno().trim()) == null) {
            return "班级编号不存在，请先在班级表中添加该班级。";
        }
        Student exists = dao.getBySno(student.getSno());
        if (exists != null) {
            return "学号已存在！";
        }
        student.setPassword(PasswordUtil.hash(student.getPassword()));
        dao.insert(student);
        return null;
    }

    public String update(Student student) {
        if (student.getSno() == null || student.getSno().equals("")) {
            return "被修改学生学号不可为空！";
        }
        if (student.getClazzno() != null && hasText(student.getClazzno())
                && clazzDao.getByClazzno(student.getClazzno().trim()) == null) {
            return "班级编号不存在。";
        }
        if (student.getPassword() != null && student.getPassword().trim().isEmpty()) {
            student.setPassword(null);
        } else if (student.getPassword() != null) {
            student.setPassword(PasswordUtil.hash(student.getPassword()));
        }
        dao.update(student);
        return null;
    }
}
