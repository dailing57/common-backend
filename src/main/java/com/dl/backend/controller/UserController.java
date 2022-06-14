package com.dl.backend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.dl.backend.entity.User;
import com.dl.backend.entity.result.ResultBean;
import com.dl.backend.exception.BusinessException;
import com.dl.backend.exception.CodeEnum;
import com.dl.backend.service.IUserService;
import com.dl.backend.util.CheckUtil;
import com.dl.backend.util.JWTUtils;
import com.dl.backend.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/user")
@Api("用户信息管理")
public class UserController {
    @Autowired
    IUserService userService;

    @PostMapping("register")
    @ApiOperation(value = "注册用户", notes = "注册用户")
    public ResultBean register(@ApiParam(value = "手机号", required = true) @Param("phone") String phone, @ApiParam(value = "密码", required = true) @Param("password") String password) {
        CheckUtil.requireNotEmpty(phone, "phone不能为空");
        if (!StringUtils.isMobilePhone(phone)) {
            throw new BusinessException(CodeEnum.BAD_REQUEST.getCode(), "手机号格式错误");
        }
        CheckUtil.requireNotEmpty(password, "password不能为空");
        if (password.length() != 6) {
            throw new BusinessException(CodeEnum.BAD_REQUEST.getCode(), "密码需要6位");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        User one = userService.getOne(queryWrapper);
        if (one == null) {
            User user = new User();
            user.setPhone(phone);
            user.setPassword(password);
            String str = RandomStringUtils.randomAlphanumeric(5);
            user.setUsername("用户" + str);
            LocalDateTime localDateTime = LocalDateTime.now();
            userService.save(user);
            return ResultBean.success(null);
        } else {
            throw new BusinessException(CodeEnum.COMMON_FAIL_CODE, "该手机号已注册");
        }

    }

    @PostMapping("login")
    @ApiOperation(value = "登录", notes = "登录")
    public ResultBean<User> login(@ApiParam(value = "手机号", required = true) @Param("phone") String phone, @ApiParam(value = "密码", required = true) @Param("password") String password) {
        CheckUtil.requireNotEmpty(phone, "phone不能为空");
        if (!StringUtils.isMobilePhone(phone)) {
            throw new BusinessException(CodeEnum.BAD_REQUEST.getCode(), "手机号格式错误");
        }
        CheckUtil.requireNotEmpty(password, "password不能为空");
        if (password.length() != 6) {
            throw new BusinessException(CodeEnum.BAD_REQUEST.getCode(), "密码需要6位");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("phone", phone)
                .eq("password", password);
        User one = userService.getOne(queryWrapper);
        if (one == null) {
            throw new BusinessException(CodeEnum.COMMON_FAIL_CODE, "登录失败，手机号或密码不对");
        } else {
            User user = new User();
            //属性拷贝，貌似有三方库更方便，我没去研究
            user.setPhone(one.getPhone());
            user.setUsername(one.getUsername());
            user.setId(one.getId());
            //生成token
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("id", user.getId() + "");
            //生成JWT令牌
            String token = JWTUtils.getToken(tokenMap);
            user.token = token;
            return ResultBean.success(user);
        }

    }

    @GetMapping("user_info")
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息")
    public ResultBean<User> userInfo(@RequestHeader String token) {
        int userId = JWTUtils.getIdByToken(token);//这里是之前的拦截器放行的。所以一定正确

        User one = userService.getById(userId);
        if (one == null) {
            throw new BusinessException(CodeEnum.COMMON_FAIL_CODE, "获取用户信息失败");
        } else {
            User user = new User();
            //属性拷贝，貌似有三方库更方便，我没去研究
            user.setPhone(one.getPhone());
            user.setUsername(one.getUsername());
            user.setId(one.getId());
            return ResultBean.success(user);
        }
    }

    @PostMapping("icon_url")
    @ApiOperation(value = "修改头像", notes = "修改头像")
    public ResultBean iconUrl(@ApiParam(value = "图片链接", required = true) @Param("iconUrl") String iconUrl, @RequestHeader String token) {
        CheckUtil.requireNotEmpty(iconUrl, "iconUrl不能为空");
        int userId = JWTUtils.getIdByToken(token);//这里是之前的拦截器放行的。所以一定正确
        UpdateWrapper updateWrapper = new UpdateWrapper();

        updateWrapper.eq("id", userId);

        updateWrapper.set("icon_url", iconUrl);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        String localTime = df.format(time);
        updateWrapper.set("update_time", localTime);

        boolean update = userService.update(null, updateWrapper);
        if (update) {
            return ResultBean.success(null);
        } else {
            throw new BusinessException(CodeEnum.COMMON_FAIL_CODE, "更新头像失败");
        }
    }

    @PostMapping("change_username")
    @ApiOperation(value = "修改用户名", notes = "修改用户名")
    public ResultBean changeUserName(@ApiParam(value = "用户名", required = true) @Param("username") String username, @RequestHeader String token) {
        CheckUtil.requireNotEmpty(username, "username不能为空");
        int userId = JWTUtils.getIdByToken(token);//这里是之前的拦截器放行的。所以一定正确
        UpdateWrapper updateWrapper = new UpdateWrapper();

        updateWrapper.eq("id", userId);

        updateWrapper.set("username", username);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        String localTime = df.format(time);
        updateWrapper.set("update_time", localTime);

        boolean update = userService.update(null, updateWrapper);
        if (update) {
            return ResultBean.success(null);
        } else {
            throw new BusinessException(CodeEnum.COMMON_FAIL_CODE, "修改用户名失败");
        }
    }
}
