package com.dl.backend.config;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.dl.backend.exception.BusinessException;
import com.dl.backend.exception.CodeEnum;
import com.dl.backend.util.JWTUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT验证拦截器
 */
public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌建议是放在请求头中，获取请求头中令牌
        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            token = "";
        }
        try {
            JWTUtils.verify(token);//验证令牌
            return true;//放行请求
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
//            map.put("msg","无效签名");
            throw new BusinessException(CodeEnum.TOKEN_BAD);
        } catch (TokenExpiredException e) {
            e.printStackTrace();
//            map.put("msg","token过期");
            throw new BusinessException(CodeEnum.TOKEN_EXPIRED);
        } catch (AlgorithmMismatchException e) {
            e.printStackTrace();
//            map.put("msg","token算法不一致");
            throw new BusinessException(CodeEnum.TOKEN_BAD);
        } catch (Exception e) {
            e.printStackTrace();
//            map.put("msg","token失效");
            throw new BusinessException(CodeEnum.TOKEN_BAD);
        }
    }
}
