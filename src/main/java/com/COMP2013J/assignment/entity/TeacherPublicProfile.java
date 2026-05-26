package com.COMP2013J.assignment.entity;

/**
 * 面向学生的教师公开信息（不含密码）。
 */
public class TeacherPublicProfile {

    private String tno;
    private String tname;
    private int courseCount;
    /** 开设课程名称摘要，顿号分隔 */
    private String courseNames;

    public String getTno() {
        return tno;
    }

    public void setTno(String tno) {
        this.tno = tno;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public int getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(int courseCount) {
        this.courseCount = courseCount;
    }

    public String getCourseNames() {
        return courseNames;
    }

    public void setCourseNames(String courseNames) {
        this.courseNames = courseNames;
    }
}
