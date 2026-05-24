package com.COMP2013J.assignment.service;

import com.COMP2013J.assignment.dao.ClazzDao;
import com.COMP2013J.assignment.dao.StudentDao;
import com.COMP2013J.assignment.entity.Clazz;
import com.COMP2013J.assignment.utils.vo.PagerVO;

import java.util.ArrayList;
import java.util.List;

public class ClazzService {
    ClazzDao dao = new ClazzDao();
    StudentDao studentDao = new StudentDao();

    public int count() {
        return dao.count();
    }

    public Clazz getByClazzno(String clazzno) {
        return dao.getByClazzno(clazzno);
    }

    public List<Clazz> statistics() {
        return dao.statistics();
    }

    public PagerVO<Clazz> page(int current, int size, String clazzno, String name) {
        StringBuilder whereSql = new StringBuilder(" where 1=1 ");
        List<Object> params = new ArrayList<>();
        if (name != null && !name.trim().isEmpty()) {
            whereSql.append(" and name like ?");
            params.add("%" + name.trim() + "%");
        }
        if (clazzno != null && !clazzno.trim().isEmpty()) {
            whereSql.append(" and clazzno like ?");
            params.add("%" + clazzno.trim() + "%");
        }
        return dao.page(current, size, whereSql.toString(), params.toArray());
    }

    public String delete(String clazzno) {
        int count = studentDao.countByClazzno(clazzno);
        if (count > 0) {
            return "删除失败，该班级已有 " + count + " 人";
        }
        dao.delete(clazzno);
        return null;
    }

    public String insert(Clazz clazz) {
        if (clazz.getClazzno() == null || clazz.getClazzno().trim().isEmpty()) {
            return "班级编号不可为空！";
        }
        if (clazz.getName() == null || clazz.getName().trim().isEmpty()) {
            return "班级名不可为空！";
        }
        if (dao.getByClazzno(clazz.getClazzno().trim()) != null) {
            return "班级编号已存在！";
        }
        dao.insert(clazz);
        return null;
    }

    public String update(Clazz clazz) {
        if (clazz.getClazzno() == null || clazz.getClazzno().trim().isEmpty()) {
            return "班级编号不可为空！";
        }
        if (clazz.getName() == null || clazz.getName().trim().isEmpty()) {
            return "班级名不可为空！";
        }
        if (dao.getByClazzno(clazz.getClazzno().trim()) == null) {
            return "班级不存在";
        }
        dao.update(clazz);
        return null;
    }
}
