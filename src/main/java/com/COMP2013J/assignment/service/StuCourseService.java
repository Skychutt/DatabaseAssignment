package com.COMP2013J.assignment.service;

import com.COMP2013J.assignment.dao.CourseDao;
import com.COMP2013J.assignment.dao.StuCourseDao;
import com.COMP2013J.assignment.entity.Course;
import com.COMP2013J.assignment.entity.StuCourse;
import com.COMP2013J.assignment.entity.StuCourseView;
import com.COMP2013J.assignment.utils.vo.PagerVO;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class StuCourseService {

    private final StuCourseDao stuCourseDao = new StuCourseDao();
    private final CourseDao courseDao = new CourseDao();

    public List<StuCourseView> listMyEnrollments(String sno) {
        return stuCourseDao.listByStudent(sno);
    }

    public PagerVO<Course> pageChoosable(String sno, int current, int size) {
        PagerVO<Course> p = courseDao.pageChoosableForStudent(sno, current, size);
        if (p != null) {
            p.init();
        }
        return p;
    }

    public List<StuCourseView> listForTeacher(String tno) {
        return stuCourseDao.listForTeacher(tno);
    }

    public List<StuCourseView> listAllForAdmin() {
        return stuCourseDao.listAll();
    }

    public int countAllEnrollments() {
        return stuCourseDao.countAll();
    }

    public String choose(String sno, String cno) {
        if (sno == null || sno.trim().isEmpty() || cno == null || cno.trim().isEmpty()) {
            return "参数不完整";
        }
        sno = sno.trim();
        cno = cno.trim();
        Course course = courseDao.getByCno(cno);
        if (course == null) {
            return "课程不存在";
        }
        if (!inChooseWindow(course)) {
            return "当前不在该课程的选课时间内";
        }
        if (stuCourseDao.exists(cno, sno)) {
            return "您已选过该课程";
        }
        if (!courseDao.tryIncrementEnrollment(cno)) {
            return "课程人数已满";
        }
        StuCourse row = new StuCourse();
        row.setCno(cno);
        row.setSno(sno);
        row.setChosetime(new Date(System.currentTimeMillis()));
        row.setScore(null);
        row.setEvaluation(null);
        if (stuCourseDao.insert(row) <= 0) {
            courseDao.changeEnrollmentCount(cno, -1);
            return "选课失败，请重试";
        }
        return null;
    }

    public String drop(String sno, String cno) {
        if (sno == null || cno == null) {
            return "参数不完整";
        }
        sno = sno.trim();
        cno = cno.trim();
        Course course = courseDao.getByCno(cno);
        if (course == null) {
            return "课程不存在";
        }
        if (!inChooseWindow(course)) {
            return "当前不在该课程的退选时间内";
        }
        if (!stuCourseDao.exists(cno, sno)) {
            return "未找到选课记录";
        }
        stuCourseDao.delete(cno, sno);
        courseDao.changeEnrollmentCount(cno, -1);
        return null;
    }

    public String grade(boolean isAdmin, String operatorTno, String cno, String sno, Double score, String evaluation) {
        if (cno == null || sno == null) {
            return "参数不完整";
        }
        cno = cno.trim();
        sno = sno.trim();
        if (!stuCourseDao.exists(cno, sno)) {
            return "选课记录不存在";
        }
        Course course = courseDao.getByCno(cno);
        if (course == null) {
            return "课程不存在";
        }
        if (!isAdmin) {
            if (operatorTno == null || !operatorTno.equals(course.getTno())) {
                return "您不是该课程的任课教师，无权打分";
            }
        }
        stuCourseDao.updateGrade(cno, sno, score, evaluation);
        return null;
    }

    public String adminRemoveEnrollment(String cno, String sno) {
        if (!stuCourseDao.exists(cno.trim(), sno.trim())) {
            return "记录不存在";
        }
        stuCourseDao.delete(cno.trim(), sno.trim());
        courseDao.changeEnrollmentCount(cno.trim(), -1);
        return null;
    }

    private boolean inChooseWindow(Course c) {
        java.util.Date now = new java.util.Date();
        if (c.getBegindate() != null && now.before(dayStart(c.getBegindate()))) {
            return false;
        }
        if (c.getEnddate() != null && !now.before(dayAfter(c.getEnddate()))) {
            return false;
        }
        return true;
    }

    private java.util.Date dayStart(java.util.Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private java.util.Date dayAfter(java.util.Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
