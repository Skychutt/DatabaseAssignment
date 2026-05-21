package com.COMP2013J.assignment.utils;
import java.io.Serializable;
/**
 * ajax请求返回内容
 * success表示成功与否
 * message是提示消息
 * data是数据
 */
public class ApiResult implements Serializable {
    private Boolean success;
    private String message;
    private Object data;
    public ApiResult(Boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
    public static String json(Boolean success, String message, Object data) {
        return "{\"success\":" + success +
                ",\"message\":\"" + escapeJson(message) + "\"," +
                "\"data\":" + toJsonValue(data) + "}";
    }
    public static String json(Boolean success, String message) {
        return json(success, message, null);
    }

    private static String toJsonValue(Object value) {
        if (value == null) {
            return "null";
        }
        if (value instanceof Number || value instanceof Boolean) {
            return String.valueOf(value);
        }
        return "\"" + escapeJson(String.valueOf(value)) + "\"";
    }

    private static String escapeJson(String value) {
        if (value == null) {
            return "";
        }
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "\\r")
                .replace("\n", "\\n");
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

