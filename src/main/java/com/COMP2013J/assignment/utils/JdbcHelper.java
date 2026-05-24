package com.COMP2013J.assignment.utils;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * JDBC 工具类：配置从 classpath:jdbc.properties 读取，避免密码硬编码在源码中。
 */
public class JdbcHelper {

    private static final String className = "com.mysql.cj.jdbc.Driver";
    private static final String url;
    private static final String user;
    private static final String pass;

    static {
        Properties props = new Properties();
        try (InputStream in = JdbcHelper.class.getClassLoader().getResourceAsStream("jdbc.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError("无法加载 jdbc.properties: " + e.getMessage());
        }
        url = props.getProperty("jdbc.url",
                "jdbc:mysql://localhost:3306/stu_manage?serverTimezone=GMT%2B8&characterEncoding=utf-8&allowPublicKeyRetrieval=true&useSSL=false");
        user = props.getProperty("jdbc.user", "root");
        pass = props.getProperty("jdbc.password", "");
    }

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    static {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public JdbcHelper() {
        try {
            conn = DriverManager.getConnection(url, user, pass);
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public ResultSet executeQuery(String sql, Object... params) {
        try {
            if (conn == null) {
                return null;
            }
            pstmt = conn.prepareStatement(sql);
            bindParams(params);
            rs = pstmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public int executeUpdate(String sql, Object... params) {
        int row = -1;
        try {
            if (conn == null) {
                return -1;
            }
            pstmt = conn.prepareStatement(sql);
            bindParams(params);
            row = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    private void bindParams(Object[] params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
        }
    }

    public void closeDB() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
