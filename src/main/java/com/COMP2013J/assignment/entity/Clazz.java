package com.COMP2013J.assignment.entity;

public class Clazz {
    private int stuCount;
    private String clazzno;//classno
    private String name;//班级名

    public String getClazzno() {
        return clazzno;
    }

    public void setClazzno(String clazzno) {
        this.clazzno = clazzno;
    }

    public int getStuCount(){
        return this.stuCount;
    }
    public void setStuCount(int stuCount){
        this.stuCount = stuCount;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
