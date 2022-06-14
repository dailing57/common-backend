package com.dl.backend.util;


import com.dl.backend.exception.BusinessException;
import com.dl.backend.exception.CodeEnum;

public class CheckUtil {
    public static <T> T requireNotNull(T obj) {
        if (obj == null)
            throw new NullPointerException();
        return obj;
    }

    public static String requireNotEmpty(String s, String msg) {
        if (s == null || s.isEmpty()) {
            throw new BusinessException(CodeEnum.BAD_REQUEST.getCode(), msg);
        }
        return s;
    }

    public static Integer requireNotEmpty(Integer i, String msg) {
        if (i == null || i == 0) {
            throw new BusinessException(CodeEnum.BAD_REQUEST.getCode(), msg);
        }
        return i;
    }
}
