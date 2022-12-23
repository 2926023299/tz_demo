package com.example.tz_demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.tz_demo.dto.LoginDto;
import com.example.tz_demo.entity.ManageUser;
import com.example.tz_demo.entity.common.R;

public interface LoginService extends IService<ManageUser> {


    R login(LoginDto user);

    R register(ManageUser user);
}
