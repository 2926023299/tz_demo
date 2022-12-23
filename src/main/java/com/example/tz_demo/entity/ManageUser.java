package com.example.tz_demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * APP用户信息表
 * </p>
 */
@Data
@TableName("manage_user")
@ApiModel(description = "用户管理")
public class ManageUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 密码、通信等加密盐
     */
    @TableField("salt")
    @ApiModelProperty(value = "加密盐")
    private String salt;

    /**
     * 用户名
     */
    @TableField("name")
    @ApiModelProperty(value = "用户名 ")
    private String name;

    /**
     * 密码,md5加密
     */
    @TableField("password")
    @ApiModelProperty(value = "密码")
    private String password;
}