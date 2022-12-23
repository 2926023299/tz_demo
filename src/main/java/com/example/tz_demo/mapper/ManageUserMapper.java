package com.example.tz_demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.tz_demo.entity.ManageUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ManageUserMapper extends BaseMapper<ManageUser> {
}
