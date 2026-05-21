package com.COMP2013J.assignment.service;

import com.COMP2013J.assignment.dao.CourseDao;
import com.COMP2013J.assignment.dao.TeacherDao;
import com.COMP2013J.assignment.entity.Course;
import com.COMP2013J.assignment.utils.vo.PagerVO;

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
        String whereSql = " where 1=1 ";
        if (hasText(cname)) {
            whereSql += " and cname like '%" + escapeSqlLike(cname.trim()) + "%'";
        }
        if (hasText(cno)) {
            whereSql += " and cno = '" + escapeSqlLiteral(cno.trim()) + "'";
        }
        if (hasText(tno)) {
            whereSql += " and tno = '" + escapeSqlLiteral(tno.trim()) + "'";
        }
        return dao.page(current, size, whereSql);
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private String escapeSqlLiteral(String s) {
        return s.replace("'", "''");
    }

    private String escapeSqlLike(String s) {
        return s.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_").replace("'", "''");
    }

    public void delete(String cno) {
        dao.delete(cno);
    }

    public String insert(Course course) {
        if (!hasText(course.getCno())) {
            return "课程号不可为空！";
        }
        if (!hasText(course.getTno())) {
            return "教师工号不可为空！";
        }
        if (!hasText(course.getCname())) {
            return "课程名不可为空！";
        }
        if (teacherDao.getByTno(course.getTno().trim()) == null) {
            return "教师工号不存在，请先在教师表中添加该教师。";
        }
        if (dao.getByCno(course.getCno().trim()) != null) {
            return "课程号已存在！";
        }
        if (course.getCount() == null) {
            course.setCount(0);
        }
        dao.insert(course);
        return null;
    }

    public String update(Course course) {
        if (!hasText(course.getCno())) {
            return "课程号不可为空！";
        }
        if (course.getTno() != null && hasText(course.getTno())
                && teacherDao.getByTno(course.getTno().trim()) == null) {
            return "教师工号不存在。";
        }
        dao.update(course);
        return null;
    }

    public String deleteWithCheck(String cno) {
        if (!hasText(cno)) {
            return "课程号不可为空";
        }
        int n = dao.countStuCourseByCno(cno.trim());
        if (n > 0) {
            return "删除失败：该课程已有 " + n + " 条选课记录。";
        }
        dao.delete(cno.trim());
        return null;
    }
}
