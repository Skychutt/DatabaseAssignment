package com.COMP2013J.assignment.entity;

import java.util.Date;

public class StuCourse {

    private String cno;  //课程号
    private String sno;  //学生号
    private Date chosetime;  //选课时间
    private Double score;  //期末得分
    private String evaluation;  //期末评价

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public Date getChosetime() {
        return chosetime;
    }

    public void setChosetime(Date chosetime) {
        this.chosetime = chosetime;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
