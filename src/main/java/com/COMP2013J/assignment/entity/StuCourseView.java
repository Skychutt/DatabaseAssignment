package com.COMP2013J.assignment.entity;

import java.util.Date;

/**
 * 选课记录展示（含课程名、学生姓名等关联信息）
 */
public class StuCourseView {

    private String cno;
    private String cname;
    private String sno;
    private String studentName;
    private Date chosetime;
    private Double score;
    private String evaluation;
    private String teacherTno;
    private String teacherName;

    public String getTeacherTno() {
        return teacherTno;
    }

    public void setTeacherTno(String teacherTno) {
        this.teacherTno = teacherTno;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Date getChosetime() {
        return chosetime;
    }

    public void setChosetime(Date chosetime) {
        this.chosetime = chosetime;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }
}
