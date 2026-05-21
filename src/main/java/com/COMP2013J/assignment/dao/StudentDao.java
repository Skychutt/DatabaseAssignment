package com.COMP2013J.assignment.dao;
import com.COMP2013J.assignment.entity.Student;
import com.COMP2013J.assignment.utils.JdbcHelper;
import com.COMP2013J.assignment.utils.vo.PagerVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {


    //新增学生
    public int insert(Student student){
        JdbcHelper helper = new JdbcHelper();
        int res = helper.executeUpdate("insert into tb_student values(?,?,?,?,?,?,?,?,?)"
                ,student.getSno(),student.getPassword(),student.getName(),student.getTele()
                ,student.getEnterdate(),student.getAge(),student.getGender()
                ,student.getAddress(),student.getClazzno()
        );
        helper.closeDB();
        return res;
    }
    //修改学生
    public int update(Student student){
        JdbcHelper helper = new JdbcHelper();
        int res = 0;
        //为null的属性不做更新
        String sql = "update tb_student set ";
        List<Object> params = new ArrayList<>();
        if(student.getPassword()!=null){
            sql += "password = ?,";
            params.add(student.getPassword());
        }
        if(student.getName()!=null){
            sql += "name = ?,";
            params.add(student.getName());
        }
        if(student.getTele()!=null){
            sql += "tele = ?,";
            params.add(student.getTele());
        }
        if(student.getEnterdate()!=null){
            sql += "enterdate = ?,";
            params.add(student.getEnterdate());
        }
        if(student.getAge()!=null){
            sql += "age = ?,";
            params.add(student.getAge());
        }
        if(student.getGender()!=null){
            sql += "gender = ?,";
            params.add(student.getGender());
        }if(student.getAddress()!=null){
            sql += "address = ?,";
            params.add(student.getAddress());
        }
        if(student.getClazzno()!=null){
            sql += "clazzno = ?,";
            params.add(student.getClazzno());
        }
        sql = sql.substring(0,sql.length()-1);
        sql+=" where sno = '" + student.getSno() + "'";
        res = helper.executeUpdate(sql,params.toArray());
        helper.closeDB();
        return res;
    }



    public int delete(String sno){
        JdbcHelper helper = new JdbcHelper();
        int res = helper.executeUpdate("delete from tb_student where sno = ?",sno);
        helper.closeDB();
        return res;
    }


    public PagerVO<Student> page(int current, int size, String whereSql){
        PagerVO<Student> pagerVO = new PagerVO<>();
        pagerVO.setCurrent(current);
        pagerVO.setSize(size);
        JdbcHelper helper = new JdbcHelper();
        if(whereSql == null){
            whereSql = " ";
        }
        ResultSet resultSet;
        try {
            resultSet = helper.executeQuery("select count(1) from tb_student "+whereSql);
            resultSet.next();
            int total = resultSet.getInt(1);
            pagerVO.setTotal(total);

            resultSet = helper.executeQuery("select * from tb_student " + whereSql + " limit " + ((current-1)*size) + "," + size);
            List<Student> list = new ArrayList<>();
            while (resultSet.next()){
                Student e = toEntity(resultSet);
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

    private Student toEntity(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setSno(resultSet.getString("sno"));
        student.setPassword(resultSet.getString("password"));
        student.setName(resultSet.getString("name"));
        student.setTele(resultSet.getString("tele"));
        student.setEnterdate(resultSet.getDate("enterdate"));
        student.setAge(resultSet.getInt("age"));
        student.setGender(resultSet.getString("gender"));
        student.setAddress(resultSet.getString("address"));
        student.setClazzno(resultSet.getString("clazzno"));
        return student;
    }

    /**
     * 获取一个班学生数量
     * @return
     */
    public int count(String whereSql){
        if(whereSql == null){
            whereSql = "";
        }

        JdbcHelper helper = new JdbcHelper();
        ResultSet resultSet = helper.executeQuery("select count(1) from tb_student" + whereSql);
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


    /**
     * 获取总共学生数量
     * @return
     */
    public int count() {
        JdbcHelper helper = new JdbcHelper();
        ResultSet resultSet = helper.executeQuery("select count(1) from tb_student");
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


    public Student getBySno(String sno){
        JdbcHelper helper = new JdbcHelper();
        ResultSet resultSet = helper.executeQuery("select * from tb_student where sno = ?",sno);
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

