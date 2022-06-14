package com.dl.backend.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JWTUtils {
    private static final String SIGNATURE = "token!@#$%^7890";

    /**
     * 生成token
     * @param map //传入payload
     * @return 返回token
     */
    public static String getToken(Map<String,String> map){
        JWTCreator.Builder builder = JWT.create();
        map.forEach(builder::withClaim);
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR,24*20);//过期时间
//        instance.add(Calendar.SECOND,30);//过期时间 测试使用
        builder.withExpiresAt(instance.getTime());
        return builder.sign(Algorithm.HMAC256(SIGNATURE)).toString();
    }

    /**
     * 验证token
     * @param token
     */
    public static void verify(String token){
        JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
    }

    /**
     * 获取token中payload
     * @param token
     * @return
     */
    public static DecodedJWT getToken(String token){
        return JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
    }
    public static int getIdByToken(String token){
        DecodedJWT decodedJWT = getToken(token);
        return Integer.parseInt(decodedJWT.getClaim("id").asString());
    }
}
