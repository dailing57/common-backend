package com.dl.backend.entity.result;

import com.dl.backend.exception.BusinessException;
import com.dl.backend.exception.CodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 统一的接口返回类
 * 记住，返回给客户端的东西要通用，简洁，因为用户不关心你具体哪错了，想了解详情服务端就打印
 */
@Data
@ApiModel(description = "统一响应结果")
public class ResultBean<T> {
    /**
     * 响应代码
     */
    @ApiModelProperty(value = "响应代码")
    public int code;

    /**
     * 响应消息
     */
    @ApiModelProperty(value = "响应消息")
    public String msg;

    /**
     * 响应结果
     */
    @ApiModelProperty(value = "响应结果")
    public T data;

    public ResultBean() {

    }

    public static <R> ResultBean<R> success(R data) {
        ResultBean<R> resultBean = new ResultBean<R>();
        resultBean.code = CodeEnum.SUCCESS.getCode();
        resultBean.msg = CodeEnum.SUCCESS.getMsg();
        resultBean.data = data;
        return resultBean;
    }

    public static <R> ResultBean<R> success(R data, String msg) {
        ResultBean<R> resultBean = new ResultBean<R>();
        resultBean.code = CodeEnum.SUCCESS.getCode();
        resultBean.msg = msg;
        resultBean.data = data;
        return resultBean;
    }

    public static ResultBean fail(BusinessException businessException) {
        ResultBean resultBean = new ResultBean();
        resultBean.code = businessException.getCode();
        resultBean.msg = businessException.getMsg();
        resultBean.data = null;
        return resultBean;
    }

}
