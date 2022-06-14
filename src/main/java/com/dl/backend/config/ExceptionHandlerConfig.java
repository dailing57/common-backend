package com.dl.backend.config;

import com.dl.backend.entity.result.ResultBean;
import com.dl.backend.exception.BusinessException;
import com.dl.backend.exception.CodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * RestControllerAdvice，统一异常处理
 * 写业务代码时有异常直接 throw new BusinessException(CodeEnum.BAD_REQUEST)类似
 * 这里能捕获到
 * 如果是没想到的异常，这里也能捕获到
 * 统一返回固定数据格式
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandlerConfig {
    BusinessException unkonwn_exception = new BusinessException(CodeEnum.UNKNOWN);
    BusinessException internal_exception = new BusinessException(CodeEnum.INTERNAL_SERVER_ERROR);

    /**
     * 业务异常处理
     *
     * @param e 业务异常
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResultBean exceptionHandler(BusinessException e) {
        log.error(e.toString());
        e.printStackTrace();
        return ResultBean.fail(e);
    }

    /**
     * 未知异常处理
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultBean exceptionHandler(Exception e) {
        // 把错误信息输入到日志中
        log.error(e.toString());
        e.printStackTrace();
        return ResultBean.fail(unkonwn_exception);
    }


    /**
     * 空指针异常
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public ResultBean exceptionHandler(NullPointerException e) {
        log.error(e.toString());
        e.printStackTrace();
        return ResultBean.fail(internal_exception);
    }
}
