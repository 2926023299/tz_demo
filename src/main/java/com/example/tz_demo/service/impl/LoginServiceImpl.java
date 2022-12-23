package com.example.tz_demo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tz_demo.common.AppJwtUtil;
import com.example.tz_demo.dto.LoginDto;
import com.example.tz_demo.entity.ManageUser;
import com.example.tz_demo.entity.common.R;
import com.example.tz_demo.exception.CustomException;
import com.example.tz_demo.mapper.ManageUserMapper;
import com.example.tz_demo.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class LoginServiceImpl extends ServiceImpl<ManageUserMapper, ManageUser> implements LoginService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public R login(LoginDto dto) {

        ManageUser user = null;

        if (StringUtils.isNotBlank(dto.getName()) && StringUtils.isNotBlank(dto.getPassword())) {
            //1.1 根据账号密码查询用户信息
            user = getOne(Wrappers.<ManageUser>lambdaQuery()
                    .eq(ManageUser::getName, dto.getName()));
            if (user == null) {
                return R.error("账号或密码错误");
            }
        } else {
            throw new CustomException("账号或密码不能为空");
        }

        //1.2 比对密码
        String salt = user.getSalt();
        String pswd = DigestUtils.md5DigestAsHex((dto.getPassword() + salt).getBytes());
        if (!pswd.equals(user.getPassword())) {
            return R.error("账号或密码错误");
        }

        //1.3 返回数据  jwt  user
        String token = AppJwtUtil.getToken(Long.valueOf(user.getId()));
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        user.setSalt("");
        user.setPassword("");
        map.put("user", user);

        redisTemplate.opsForValue().set(user.getId().toString(), token);

        return R.success(map);
    }

    @Override
    public R register(ManageUser user) {
        if (ObjectUtils.isEmpty(user)
                || StringUtils.isBlank(user.getName())
                || StringUtils.isBlank(user.getPassword())) {
            throw new CustomException("用户信息不能为空");
        }

        user.setSalt("123456");

        String pswd = DigestUtils.md5DigestAsHex((user.getPassword() + user.getSalt()).getBytes());
        user.setPassword(pswd);


        save(user);

        return R.success("注册成功");
    }

    public static void main(String[] args) {
        String pswd = DigestUtils.md5DigestAsHex("gxl".getBytes());
        System.out.println(pswd);
    }
}
