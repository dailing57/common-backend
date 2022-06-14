package com.dl.backend.exception;


/**
 * 自定义业务异常类
 */
public class BusinessException extends RuntimeException {

    private int code;
    private String msg;

    public BusinessException() {
        super();
    }

    //通用异常用这个，在枚举里找一个
    public BusinessException(CodeEnum codeEnum) {
        super("{code:" + codeEnum.getCode() + ",errorMsg:" + codeEnum.getMsg() + "}");
        this.code = codeEnum.getCode();
        this.msg = codeEnum.getMsg();
    }

    //个别异常用这个
    public BusinessException(int code, String errorMsg) {
        super("{code:" + code + ",errorMsg:" + errorMsg + "}");
        this.code = code;
        this.msg = errorMsg;
    }


    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}
