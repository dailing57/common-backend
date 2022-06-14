package com.dl.backend.exception;

/**
 * 异常枚举类
 * 一些通用的异常，如果有一些个别出现的异常，可以不定义在这，直接new一个
 */
public enum CodeEnum {

    //200
    SUCCESS(200,"成功"),
    //400
    BAD_REQUEST(400, "请求数据格式不正确!"),
    TOKEN_EXPIRED(401, "登录凭证过期!"),
    TOKEN_BAD(402, "登录凭证错误!"),
    NO_PERMISSION(403, "无权限"),
    // 500
    INTERNAL_SERVER_ERROR(500, "服务器内部错误!"),
    SERVICE_UNAVAILABLE(503, "服务器正忙，请稍后再试!"),
    // 未知异常
    UNKNOWN(10000, "未知异常!");

    /**
     * 错误码
     */
    private int code;
    public static final int COMMON_FAIL_CODE = -1;
    /**
     * 错误描述
     */
    private String msg;

    CodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}