package com.COMP2013J.assignment.service;
import com.COMP2013J.assignment.dao.ClazzDao;
import com.COMP2013J.assignment.dao.StudentDao;
import com.COMP2013J.assignment.entity.Clazz;
import com.COMP2013J.assignment.utils.vo.PagerVO;

import java.util.List;
public class ClazzService {
    ClazzDao dao = new ClazzDao();
    public int count(){
        return dao.count();
    }
    public Clazz getByClazzno(String clazzno) {
        return dao.getByClazzno(clazzno);
    }
    public List<Clazz> statistics(){
        return dao.statistics();
    }


    public PagerVO<Clazz> page(int current, int size, String clazzno, String name) {
        String whereSql = " where 1=1 ";
        if(name!=null){
            whereSql += " and name like '%" + name + "%'";
        }
        if(clazzno!=null){
            whereSql += " and clazzno like '%" + clazzno + "%'";
        }
        return dao.page(current,size,whereSql);
    }

    StudentDao studentDao = new StudentDao();

    public String delete(String clazzno){
        int count = studentDao.count(" where clazzno = '" + clazzno + "'");
        if(count>0){
            return "删除失败，该班级已有"+count+"人";
        }
        dao.delete(clazzno);
        return null;
    }

    public String insert(Clazz clazz){
        if(clazz.getClazzno() == null || clazz.getClazzno().equals("")){
            return "班级编号不可为空！";
        }
        if(clazz.getName() == null || clazz.getName().equals("")){
            return "班级名不可为空！";
        }
        //检查
        Clazz exists = dao.getByClazzno(clazz.getClazzno());
        if(exists!=null){
            return "班级编号已存在！";
        }
        dao.insert(clazz);
        return null;
    }

    public String update(Clazz clazz){
        if(clazz.getClazzno() == null || clazz.getClazzno().equals("")){
            return "班级编号不可为空！";
        }
        dao.update(clazz);
        return null;
    }

//    static void main() {
//
//        ClazzService cl = new ClazzService();
//        Clazz c = new Clazz();
//        c.setClazzno(String.valueOf(2));
//        c.setName("ruan");
//        c.setClazzno(String.valueOf(1005));
//        String r = cl.insert(c);
//        System.out.println(r);
//    }

}

