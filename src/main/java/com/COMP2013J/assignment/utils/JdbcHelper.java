package com.COMP2013J.assignment.utils;

import java.sql.*;
/**
 * 数据库操作公共类
 * 数据库配置信息
 * 提供最基本的和数据库交互的方法
 */
public class JdbcHelper {

	private static final String className = "com.mysql.cj.jdbc.Driver";
	private static final String url = "jdbc:mysql://localhost:3306/stu_manage?serverTimezone=GMT%2B8&characterEncoding=utf-8&allowPublicKeyRetrieval=true&useSSL=false";
	private static final String user = "root"; 
	private static final String pass = "Ctz20060712@";// 自行修改密码
	
	//测试
	public static void main(String[] args) throws SQLException {
		JdbcHelper jdbcHelper = new JdbcHelper();
		ResultSet rs = jdbcHelper.executeQuery("select * from tb_student");
		while (rs.next()){
			String sno = rs.getString("sno");
			String name = rs.getString("name");
			Integer age = rs.getInt("age");
			Date enterdate = rs.getDate("enterdate");
			System.out.println(sno);
			System.out.println(name);
			System.out.println(age);
			System.out.println(enterdate);
			System.out.println();
		}
		jdbcHelper.closeDB();
	}

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	//JdbcHelper类加载的时候，加载数据库驱动程序
	static {
		try {
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public JdbcHelper(){
		try {
			conn = DriverManager.getConnection(url,user,pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//执行查询    给 SELECT 用，返回 ResultSet（查到的数据）   Object... params 可以传 0 个、1 个、多个任意类型参数，自动打包成数组
	public ResultSet executeQuery(String sql, Object... params){
		try {
			if (conn == null) {
				return null;
			}
			pstmt = conn.prepareStatement(sql);  //创建预编译语句（最重要）
			if (params != null) {  //给？占位符赋值（循环设置）
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);
				}
			}
			rs = pstmt.executeQuery();  // 执行查询，返回结果集 这是 JDBC 自带的 PreparedStatement 方法
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	//执行增删改  专门给 INSERT、UPDATE、DELETE 用，返回的整数 = 数据库「被影响 / 改动了多少行」
	public int executeUpdate(String sql, Object... params){
		int row = -1;
		try {
			if (conn == null) {
				return -1;
			}
			pstmt = conn.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);
				}
			}
			row = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}
	
	public void closeDB(){
		try{
			if(rs != null){
				rs.close();
			}
			if(pstmt != null){
				pstmt.close();
			}
			if(conn != null){
				conn.close();
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	} 
}
