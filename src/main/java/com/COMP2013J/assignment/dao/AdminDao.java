package com.COMP2013J.assignment.dao;

import com.COMP2013J.assignment.entity.Admin;
import com.COMP2013J.assignment.utils.JdbcHelper;

import java.sql.ResultSet;

public class AdminDao {

    public Admin getByUsername(String username){
        JdbcHelper helper = new JdbcHelper();
        ResultSet resultSet = helper.executeQuery("select * from tb_admin where username = ?",username);  //? = 占位符（就是个坑，等着填数据）
        try {                                                                                                 //如果 SQL 有 2 个问号，你就传 2 个参数：
           if(resultSet.next()){                                                                              //java自动做了这件事 select * from tb_admin where username = 'admin'
               Admin admin = new Admin();
               admin.setUsername( resultSet.getString("username"));
               admin.setPassword( resultSet.getString("password"));
               return admin;
           }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            helper.closeDB();
        }
        return null;
    }

    public int updatePassword(String username, String newPassword) {
        JdbcHelper helper = new JdbcHelper();
        int result = helper.executeUpdate("update tb_admin set password = ? where username = ?", newPassword, username);
        helper.closeDB();
        return result;
    }

    public int insert(String username, String password) {
        JdbcHelper helper = new JdbcHelper();
        int res = helper.executeUpdate("insert into tb_admin values(?,?)", username, password);
        helper.closeDB();
        return res;
    }

    public java.util.List<String> listAllUsernames() {
        java.util.List<String> list = new java.util.ArrayList<>();
        JdbcHelper helper = new JdbcHelper();
        ResultSet resultSet = helper.executeQuery("select username from tb_admin order by username");
        try {
            while (resultSet != null && resultSet.next()) {
                list.add(resultSet.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            helper.closeDB();
        }
        return list;
    }

}
