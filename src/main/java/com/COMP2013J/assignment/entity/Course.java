package com.COMP2013J.assignment.entity;

import java.util.Date;


public class Course {

    private String cno;   //课程号
    private String tno;   //教师号
    private String cname;  //课程名
    private Date begindate;  // 开始选课时间
    private Date enddate;  //选课结束时间
    private Double credits;  //学分( 3,1 表示3位数，其中1位为小数，最大是99.9)
    private Integer maximum;  //限制人数
    private Integer count;  //已选人数
    private String content;  //课程内容

    public String getTno() {
        return tno;
    }

    public void setTno(String tno) {
        this.tno = tno;
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

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }

    public Double getCredits() {
        return credits;
    }

    public void setCredits(Double credits) {
        this.credits = credits;
    }

    public Date getBegindate() {
        return begindate;
    }

    public void setBegindate(Date begindate) {
        this.begindate = begindate;
    }
}
