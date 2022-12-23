package com.example.tz_demo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * author:  唐哲
 * date:    2022/12/22 14:23
 * description: 登录信息
 */
@Data
@ApiModel(value = "登录信息")
public class LoginDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "密码")
    private String password;

}
