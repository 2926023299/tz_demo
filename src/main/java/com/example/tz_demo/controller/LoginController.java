package com.example.tz_demo.controller;

import com.example.tz_demo.dto.LoginDto;
import com.example.tz_demo.entity.ManageUser;
import com.example.tz_demo.entity.common.R;
import com.example.tz_demo.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * author:  唐哲
 * date:    2022/12/22 14:23
 * description: 登录控制器
 */
@RequestMapping("/admin")
@RestController
@Api(tags = "登录控制器")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    @ApiImplicitParam(name = "LoginDto", value = "用户信息", required = true, dataType = "LoginDto")
    @ApiOperation(value = "登录", notes = "登录")
    public R login(@RequestBody LoginDto user) {

        return loginService.login(user);
    }

    @PostMapping("/register")
    @ApiImplicitParam(name = "ManageUser", value = "用户信息", required = true, dataType = "ManageUser")
    @ApiOperation(value = "注册", notes = "注册")
    public R register(@RequestBody ManageUser user){

        return loginService.register(user);
    }

}
