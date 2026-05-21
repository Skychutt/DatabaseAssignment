package com.COMP2013J.assignment.dao;
import com.COMP2013J.assignment.entity.Teacher;
import com.COMP2013J.assignment.utils.JdbcHelper;
import com.COMP2013J.assignment.utils.vo.PagerVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDao {


    //新增学生
    public int insert(Teacher teacher){
        JdbcHelper helper = new JdbcHelper();
        int res = helper.executeUpdate("insert into tb_teacher values(?,?,?)"
                ,teacher.getTno(),teacher.getPassword(),teacher.getTname()
        );
        helper.closeDB();
        return res;
    }
    //修改学生
    public int update(Teacher teacher){
        JdbcHelper helper = new JdbcHelper();
        int res = 0;
        //为null的属性不做更新
        String sql = "update tb_teacher set ";
        List<Object> params = new ArrayList<>();


        if (teacher.getPassword() != null) {
            sql += "password = ?,";
            params.add(teacher.getPassword());
        }

        if (teacher.getTname() != null) {
            sql += "tname = ?,";
            params.add(teacher.getTname());
        }

        sql = sql.substring(0,sql.length()-1);
        sql+=" where tno = '" + teacher.getTno() + "'";
        res = helper.executeUpdate(sql,params.toArray());
        helper.closeDB();
        return res;
    }



    public int delete(String tno){
        JdbcHelper helper = new JdbcHelper();
        int res = helper.executeUpdate("delete from tb_teacher where tno = ?",tno);
        helper.closeDB();
        return res;
    }


    public PagerVO<Teacher> page(int current, int size, String whereSql){
        PagerVO<Teacher> pagerVO = new PagerVO<>();
        pagerVO.setCurrent(current);
        pagerVO.setSize(size);
        JdbcHelper helper = new JdbcHelper();
        if(whereSql == null){
            whereSql = " ";
        }
        ResultSet resultSet;
        try {
            resultSet = helper.executeQuery("select count(1) from tb_teacher "+whereSql);
            resultSet.next();
            int total = resultSet.getInt(1);
            pagerVO.setTotal(total);

            resultSet = helper.executeQuery("select * from tb_teacher " + whereSql + " limit " + ((current-1)*size) + "," + size);
            List<Teacher> list = new ArrayList<>();
            while (resultSet.next()){
                Teacher e = toEntity(resultSet);
                list.add(e);
            }
            pagerVO.setList(list);
            return pagerVO;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            helper.closeDB();
        }
        return null;
    }

    private Teacher toEntity(ResultSet resultSet) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setTno(resultSet.getString("tno"));
        teacher.setPassword(resultSet.getString("password"));
        teacher.setTname(resultSet.getString("tname"));
        return teacher;
    }

//    /**
//     * 获取一个班学生数量
//     * @return
//     */
//    public int count(String whereSql){
//        if(whereSql == null){
//            whereSql = "";
//        }
//
//        JdbcHelper helper = new JdbcHelper();
//        ResultSet resultSet = helper.executeQuery("select count(1) from tb_teacher" + whereSql);
//        try {
//            resultSet.next();
//            return resultSet.getInt(1);
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            helper.closeDB();
//        }
//        return 0;
//    }


    /**
     * 获取总共老师数量
     * @return
     */
    public int count() {
        JdbcHelper helper = new JdbcHelper();
        ResultSet resultSet = helper.executeQuery("select count(1) from tb_teacher");
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


    public Teacher getByTno(String tno){
        JdbcHelper helper = new JdbcHelper();
        ResultSet resultSet = helper.executeQuery("select * from tb_teacher where tno = ?",tno);
        try {
            if(resultSet.next()){
                return toEntity(resultSet);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            helper.closeDB();
        }
        return null;
    }

}

