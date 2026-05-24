package com.COMP2013J.assignment.service;

import com.COMP2013J.assignment.dao.AdminDao;
import com.COMP2013J.assignment.entity.Admin;
import com.COMP2013J.assignment.security.PasswordUtil;

public class AdminService {
    AdminDao adminDao = new AdminDao();

    public Admin getByUsername(String username) {
        return adminDao.getByUsername(username);
    }

    public String updatePassword(String username, String newPassword) {
        if (newPassword == null || newPassword.trim().isEmpty()) {
            return "新密码不可为空！";
        }
        Admin exists = getByUsername(username);
        if (exists == null) {
            return "用户不存在！";
        }
        adminDao.updatePassword(username, PasswordUtil.hash(newPassword.trim()));
        return null;
    }

    public String register(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            return "用户名不可为空！";
        }
        if (password == null || password.trim().isEmpty()) {
            return "密码不可为空！";
        }
        Admin exists = getByUsername(username.trim());
        if (exists != null) {
            return "用户已存在！";
        }
        adminDao.insert(username.trim(), PasswordUtil.hash(password.trim()));
        return null;
    }

    public java.util.List<String> listAllUsernames() {
        return adminDao.listAllUsernames();
    }
}
