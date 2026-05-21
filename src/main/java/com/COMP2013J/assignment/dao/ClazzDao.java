package com.COMP2013J.assignment.dao;

import com.COMP2013J.assignment.entity.Clazz;
import com.COMP2013J.assignment.utils.JdbcHelper;
import com.COMP2013J.assignment.utils.vo.PagerVO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClazzDao {

    public int insert(Clazz clazz){
        JdbcHelper helper = new JdbcHelper();
        int res = helper.executeUpdate("insert into tb_clazz values(?,?)",clazz.getClazzno(),clazz.getName());
        helper.closeDB();
        return res;
    }

    public int update(Clazz clazz){
        JdbcHelper helper = new JdbcHelper();
        int res = 0;
        //为null的属性不做更新
        String sql = "update tb_clazz set name = ? where clazzno = ? ";
        res = helper.executeUpdate(sql,clazz.getName(),clazz.getClazzno());
        helper.closeDB();
        return res;
    }

    public Clazz getByClazzno(String clazzno){
        JdbcHelper helper = new JdbcHelper();
        ResultSet resultSet = helper.executeQuery("select * from tb_clazz where clazzno = ?",clazzno);
        try {
            if(resultSet.next()){
                Clazz clazz = new Clazz();
                clazz.setName(resultSet.getString("name"));
                clazz.setClazzno(resultSet.getString("clazzno"));
                return clazz;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            helper.closeDB();
        }
        return null;
    }



    public int delete(String clazzno){
        JdbcHelper helper = new JdbcHelper();
        int res = helper.executeUpdate("delete from tb_clazz where clazzno = ?",clazzno);
        helper.closeDB();
        return res;
    }


    public PagerVO<Clazz> page(int current, int size, String whereSql){
        PagerVO<Clazz> pagerVO = new PagerVO<>();
        pagerVO.setCurrent(current);
        pagerVO.setSize(size);
        JdbcHelper helper = new JdbcHelper();
        if(whereSql == null){
            whereSql = " ";
        }
        try {
            return queryPageWithClazzNoColumn(helper, pagerVO, whereSql, current, size, "clazzno");
        } catch (Exception first) {
            // 兼容历史库字段名为 classno 的情况
            try {
                return queryPageWithClazzNoColumn(helper, pagerVO, whereSql, current, size, "classno");
            } catch (Exception second) {
                second.printStackTrace();
            }
        } finally {
            helper.closeDB();
        }
        return null;
    }

    private PagerVO<Clazz> queryPageWithClazzNoColumn(JdbcHelper helper, PagerVO<Clazz> pagerVO,
                                                       String whereSql, int current, int size, String clazzNoColumn) throws Exception {
        String finalWhereSql = whereSql == null ? " " : whereSql.replace("clazzno", clazzNoColumn);
        ResultSet resultSet = helper.executeQuery("select count(1) from tb_clazz " + finalWhereSql);
        if (resultSet == null) {
            return null;
        }
        resultSet.next();
        int total = resultSet.getInt(1);
        pagerVO.setTotal(total);

        resultSet = helper.executeQuery("select * from tb_clazz " + finalWhereSql + " limit " + ((current - 1) * size) + "," + size);
        if (resultSet == null) {
            return null;
        }
        List<Clazz> list = new ArrayList<>();
        while (resultSet.next()) {
            Clazz e = new Clazz();
            e.setClazzno(resultSet.getString(clazzNoColumn));
            e.setName(resultSet.getString("name"));
            list.add(e);
        }
        pagerVO.setList(list);
        return pagerVO;
    }



    //班级数量
    public int count(){
        JdbcHelper helper = new JdbcHelper();
        ResultSet resultSet = helper.executeQuery("select count(1) from tb_clazz");
        try {
            resultSet.next();
            return resultSet.getInt(1);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            helper.closeDB();
        }
        return 0;
    }
    //班级学生统计
    public List<Clazz> statistics(){
        JdbcHelper helper = new JdbcHelper();
        ResultSet resultSet = helper.executeQuery(
                "select c.clazzno,c.name,count(1) as stuCount " +
                        "from tb_clazz c, tb_student s  " +
                        "where s.clazzno = c.clazzno  " +
                        "group by c.clazzno,c.name "
        );
        try {
            List<Clazz> clazzes = new ArrayList<>();
            while (resultSet.next()){
                Clazz clazz = new Clazz();
                clazz.setStuCount(resultSet.getInt("stuCount"));
                clazz.setClazzno(resultSet.getString("clazzno"));
                clazz.setName(resultSet.getString("name"));
                clazzes.add(clazz);
            }
            return clazzes;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            helper.closeDB();
        }
        return null;
    }
}

