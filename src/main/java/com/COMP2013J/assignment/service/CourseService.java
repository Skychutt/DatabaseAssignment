package com.COMP2013J.assignment.service;

import com.COMP2013J.assignment.dao.CourseDao;
import com.COMP2013J.assignment.dao.TeacherDao;
import com.COMP2013J.assignment.entity.Course;
import com.COMP2013J.assignment.utils.vo.PagerVO;

import java.util.ArrayList;
import java.util.List;

public class CourseService {

    private final CourseDao dao = new CourseDao();
    private final TeacherDao teacherDao = new TeacherDao();

    public int count() {
        return dao.count();
    }

    public Course getByCno(String cno) {
        return dao.getByCno(cno);
    }

    public PagerVO<Course> page(int current, int size, String cno, String cname, String tno) {
        StringBuilder whereSql = new StringBuilder(" where 1=1 ");
        List<Object> params = new ArrayList<>();
        if (hasText(cname)) {
            whereSql.append(" and cname like ?");
            params.add("%" + cname.trim() + "%");
        }
        if (hasText(cno)) {
            whereSql.append(" and cno = ?");
            params.add(cno.trim());
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

    public void delete(String cno) {
        dao.delete(cno);
    }

    public String insert(Course course, boolean adminOperator, String operatorTno) {
        if (!hasText(course.getCno())) {
            return "课程号不可为空！";
        }
        if (!hasText(course.getTno())) {
            return "教师工号不可为空！";
        }
        if (!hasText(course.getCname())) {
            return "课程名不可为空！";
        }
        if (!adminOperator) {
            course.setTno(operatorTno);
        }
        if (teacherDao.getByTno(course.getTno().trim()) == null) {
            return "教师工号不存在，请先在教师表中添加该教师。";
        }
        if (dao.getByCno(course.getCno().trim()) != null) {
            return "课程号已存在！";
        }
        course.setCount(0);
        dao.insert(course);
        return null;
    }

    public String update(Course course, boolean adminOperator, String operatorTno) {
        if (!hasText(course.getCno())) {
            return "课程号不可为空！";
        }
        Course existing = dao.getByCno(course.getCno().trim());
        if (existing == null) {
            return "课程不存在";
        }
        if (!adminOperator) {
            if (operatorTno == null || !operatorTno.equals(existing.getTno())) {
                return "您只能修改自己任教的课程";
            }
            course.setTno(operatorTno);
            course.setCount(null);
        }
        if (course.getTno() != null && hasText(course.getTno())
                && teacherDao.getByTno(course.getTno().trim()) == null) {
            return "教师工号不存在。";
        }
        dao.update(course);
        return null;
    }

    public String deleteWithCheck(String cno, boolean adminOperator, String operatorTno) {
        if (!hasText(cno)) {
            return "课程号不可为空";
        }
        Course existing = dao.getByCno(cno.trim());
        if (existing == null) {
            return "课程不存在";
        }
        if (!adminOperator && (operatorTno == null || !operatorTno.equals(existing.getTno()))) {
            return "您只能删除自己任教的课程";
        }
        int n = dao.countStuCourseByCno(cno.trim());
        if (n > 0) {
            return "删除失败：该课程已有 " + n + " 条选课记录。";
        }
        dao.delete(cno.trim());
        return null;
    }
}
