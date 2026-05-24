package com.COMP2013J.assignment.security;

import org.mindrot.jbcrypt.BCrypt;

/**
 * 密码哈希（BCrypt）。兼容旧库中的明文密码：首次登录成功后自动升级为哈希。
 */
public final class PasswordUtil {

    private static final int ROUNDS = 10;

    private PasswordUtil() {
    }

    public static String hash(String rawPassword) {
        if (rawPassword == null) {
            return null;
        }
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt(ROUNDS));
    }

    public static boolean matches(String rawPassword, String stored) {
        if (rawPassword == null || stored == null) {
            return false;
        }
        if (isBcryptHash(stored)) {
            try {
                return BCrypt.checkpw(rawPassword, stored);
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
        return stored.equals(rawPassword);
    }

    public static boolean isBcryptHash(String stored) {
        return stored != null && (stored.startsWith("$2a$") || stored.startsWith("$2b$") || stored.startsWith("$2y$"));
    }

    /** 明文匹配后应写入数据库的新哈希 */
    public static String upgradeIfNeeded(String rawPassword, String stored) {
        if (rawPassword == null || stored == null) {
            return stored;
        }
        if (isBcryptHash(stored)) {
            return stored;
        }
        if (stored.equals(rawPassword)) {
            return hash(rawPassword);
        }
        return stored;
    }
}
