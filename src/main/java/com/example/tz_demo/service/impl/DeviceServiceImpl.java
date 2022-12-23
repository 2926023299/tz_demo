package com.example.tz_demo.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.tz_demo.dto.UserDeviceDto;
import com.example.tz_demo.entity.Device;
import com.example.tz_demo.entity.ManageUser;
import com.example.tz_demo.entity.common.AppHttpCodeEnum;
import com.example.tz_demo.entity.common.PageRequestDto;
import com.example.tz_demo.entity.common.PageResponseResult;
import com.example.tz_demo.entity.common.ResponseResult;
import com.example.tz_demo.mapper.DeviceMapper;
import com.example.tz_demo.mapper.ManageUserMapper;
import com.example.tz_demo.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import static sun.audio.AudioDevice.device;

/**
 * author:  唐哲
 * date:    2022/12/23 15:57
 * description: 设备服务实现类
 */
@Service
@Slf4j
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements DeviceService {

    @Autowired
    private ManageUserMapper manageUserMapper;

    @Value("${device_default_amount}")
    private Integer deviceDefaultAmount;

    @Override
    public ResponseResult insert(Device device) {
        if (ObjectUtils.isEmpty(device)
                || StringUtils.isBlank(device.getName())
                || ObjectUtils.isEmpty(device.getType())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        if(device.getPurchaseAmount() == null){
            device.setPurchaseAmount(deviceDefaultAmount);
        }

        this.save(device);
        return ResponseResult.okResult(null);
    }

    /**
     * 修改设备信息
     * @param device
     * @return
     */
    @Override
    public ResponseResult update(Device device) {
        if (ObjectUtils.isEmpty(device)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        this.updateById(device);

        return ResponseResult.okResult(null);
    }

    /**
     * 删除设备信息
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteById(String id) {
        if (StringUtils.isBlank(id)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        this.removeById(id);

        return ResponseResult.okResult(null);
    }


    /**
     * 根据id查询设备信息
     * @param id
     * @return
     */
    @Override
    public ResponseResult getDeviceById(String id) {
        if (StringUtils.isBlank(id)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        Device device = this.getById(id);
        if (ObjectUtils.isEmpty(device)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        return ResponseResult.okResult(device);
    }

    /**
     * 分页查询设备信息
     * @param dto
     * @return
     */
    @Override
    public ResponseResult conditionsList(PageRequestDto dto) {
        if (ObjectUtils.isEmpty(dto)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //构造查询wrapper
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();

        Device device = JSON.parseObject(JSON.toJSONString(dto.getData()), Device.class);
        if (!ObjectUtils.isEmpty(device)) {
            // 根据设备名称模糊查询
            wrapper.like(!StringUtils.isBlank(device.getName()), Device::getName, device.getName());
            // 根据设备类型查询
            wrapper.like(!ObjectUtils.isEmpty(device.getType()), Device::getType, device.getType());
            // 根据金额查询
            wrapper.ge(!ObjectUtils.isEmpty(device.getPurchaseAmount()), Device::getPurchaseAmount, device.getPurchaseAmount())
                    .le(!ObjectUtils.isEmpty(device.getPurchaseAmount()), Device::getPurchaseAmount, device.getPurchaseAmount());
        }

        //分页查询
        IPage<Device> page = new Page<>(dto.getPage(), dto.getSize());

        page = this.page(page, wrapper);

        PageResponseResult pageResponseResult = new PageResponseResult();
        pageResponseResult.setCurrentPage((int) page.getCurrent());
        pageResponseResult.setSize((int) page.getSize());
        pageResponseResult.setTotal((int) page.getTotal());

        pageResponseResult.setData(page.getRecords());

        return pageResponseResult;

    }

    /**
     * 根据用户id查询设备信息
     * @param dto
     * @return
     */
    @Override
    public ResponseResult getUserDeviceList(PageRequestDto dto) {
        if(ObjectUtils.isEmpty(dto)){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        log.info("getUserDeviceList dto:{}", JSON.toJSONString(dto.getData()));
        UserDeviceDto userDeviceDto = JSON.parseObject(JSON.toJSONString(dto.getData()), UserDeviceDto.class);

        if(ObjectUtils.isEmpty(userDeviceDto) || StringUtils.isBlank(userDeviceDto.getUserId())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ManageUser manageUser = manageUserMapper.selectById(userDeviceDto.getUserId());
        if(ObjectUtils.isEmpty(manageUser)){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        //构造查询wrapper
        LambdaQueryWrapper<Device> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Device::getCreateUser, userDeviceDto.getUserId());
        wrapper.like(!StringUtils.isBlank(userDeviceDto.getName()), Device::getName, userDeviceDto.getName());
        // 根据设备类型查询
        wrapper.like(!ObjectUtils.isEmpty(userDeviceDto.getType()), Device::getType, userDeviceDto.getType());
        // 根据金额查询
        wrapper.ge(!ObjectUtils.isEmpty(userDeviceDto.getPurchaseAmount()), Device::getPurchaseAmount, userDeviceDto.getPurchaseAmount())
                .le(!ObjectUtils.isEmpty(userDeviceDto.getPurchaseAmount()), Device::getPurchaseAmount, userDeviceDto.getPurchaseAmount());

        //分页查询
        IPage<Device> page = new Page<>(dto.getPage(), dto.getSize());
        page = this.page(page, wrapper);

        //构造dto数据
        List<UserDeviceDto> dtoList = new ArrayList<>();
        page.getRecords().forEach(item ->{
            UserDeviceDto init = UserDeviceDto.init(manageUser, item);
            dtoList.add(init);
        });

        //构造分页返回结果
        PageResponseResult pageResponseResult = new PageResponseResult();
        pageResponseResult.setCurrentPage((int) page.getCurrent());
        pageResponseResult.setSize((int) page.getSize());
        pageResponseResult.setTotal((int) page.getTotal());
        pageResponseResult.setData(dtoList);

        return pageResponseResult;
    }
}
