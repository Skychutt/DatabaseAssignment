package com.COMP2013J.assignment.service;

import com.COMP2013J.assignment.dao.StudentDao;
import com.COMP2013J.assignment.entity.Student;
import com.COMP2013J.assignment.utils.vo.PagerVO;

public class StudentService {

    StudentDao dao = new StudentDao();

    public Student getBySno(String sno) {
        Student student = dao.getBySno(sno);
        return student;
    }

    public int count(){
        return dao.count();
    }

    public PagerVO<Student> page(int current, int size, String sno, String name, String gender, String clazzno) {
        String whereSql = " where 1=1 ";
        if (hasText(name)) {
            whereSql += " and name like '%" + name.trim() + "%'";
        }
        if (hasText(sno)) {
            // 学号通常唯一，优先精确匹配
            whereSql += " and sno = '" + sno.trim() + "'";
        }
        if (hasText(gender)) {
            whereSql += " and gender = '" + gender.trim() + "'";
        }
        if (hasText(clazzno)) {
            whereSql += " and clazzno = '" + clazzno.trim() + "'";
        }
        return dao.page(current,size,whereSql);
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public void delete(String sno){
        dao.delete(sno);
    }

    //新增学生
    public String insert(Student student){
        if(student.getSno() == null || student.getSno().equals("")){
            return "学生学号不可为空！";
        }if(student.getPassword() == null || student.getPassword().equals("")){
            return "密码不可为空！";
        }if(student.getName() == null || student.getName().equals("")){
            return "姓名不可为空！";
        }if(student.getClazzno() == null || student.getClazzno().equals("")){
            return "班级不可为空！";
        }
        //检查
        Student exists = dao.getBySno(student.getSno());
        if(exists!=null){
            return "学号已存在！";
        }
        dao.insert(student);
        return null;
    }
    //修改学生
    public String update(Student student){
        if(student.getSno() == null || student.getSno().equals("")){
            return "被修改学生学号不可为空！";
        }
        dao.update(student);
        return null;
    }


//    static void main() {
//        StudentService ss = new StudentService();
//        Student s = new Student();
//        s.setAge(15);
//        s.setAddress("beijing");
//        s.setClazzno(String.valueOf(1002));
//        s.setSno(String.valueOf(1423432423));
//        s.setGender("m");
//        s.setName("haha");
//        s.setPassword("na");
//        s.setTele("2e12");
//        String result = ss.insert(s);
//        System.out.println(result);
//    }
}

