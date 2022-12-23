package com.example.tz_demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.tz_demo.entity.Device;
import com.example.tz_demo.entity.common.PageRequestDto;
import com.example.tz_demo.entity.common.ResponseResult;
import org.apache.ibatis.annotations.Param;

public interface DeviceService extends IService<Device> {

    ResponseResult insert(Device device);

    ResponseResult update(Device device);

    ResponseResult deleteById(String id);

    ResponseResult getDeviceById(String id);

    ResponseResult conditionsList(PageRequestDto dto);

    ResponseResult getUserDeviceList(PageRequestDto dto);
}
