package com.dl.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("todo_user")
@ApiModel(value = "User对象", description = "")
@JsonInclude(JsonInclude.Include.NON_NULL)	//注解控制null不序列化.因为Android端如果使用kotlin不可空类型的话，会出现类型错误
public class User {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("手机号")
    private String phone;


    @ApiModelProperty("密码（明文）")
    private String password;

    @TableField(exist = false) //表示该属性不为数据库表字段，但又是必须使用的。
    public String token;
}
