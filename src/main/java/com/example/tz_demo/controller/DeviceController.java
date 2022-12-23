package com.example.tz_demo.controller;

import com.example.tz_demo.entity.Device;
import com.example.tz_demo.entity.common.PageRequestDto;
import com.example.tz_demo.entity.common.ResponseResult;
import com.example.tz_demo.service.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * author:  唐哲
 * date:    2022/12/23 14:23
 * description: 设备控制器
 */
@RequestMapping("/admin/device")
@RestController
@Api(tags = "设备控制器")
@Slf4j
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    /**
     * 添加设备
     * @param device 设备信息
     */
    @ApiImplicitParam(name = "device", value = "设备信息", required = true, dataType = "Device")
    @ApiOperation(value = "添加设备", notes = "添加设备")
    @PutMapping("/save")
    public ResponseResult save(@RequestBody Device device) {
        return deviceService.insert(device);
    }

    /**
     * 修改设备
     * @param device 设备信息
     */
    @ApiImplicitParam(name = "device", value = "设备信息", required = true, dataType = "Device")
    @ApiOperation(value = "修改设备", notes = "修改设备信息")
    @PutMapping("/update")
    public ResponseResult update(@RequestBody Device device) {
        return deviceService.update(device);
    }

    /**
     * 删除设备
     * @param id 设备id
     * @return 删除结果
     */
    @ApiImplicitParam(name = "id", value = "设备id", required = true, dataType = "String")
    @ApiOperation(value = "删除设备", notes = "根据id删除设备")
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@PathVariable("id") String id) {
        return deviceService.deleteById(id);
    }

    /**
     * 根据id查询设备
     * @param id 设备id
     * @return 设备信息
     */
    @ApiImplicitParam(name = "id", value = "设备id", required = true, dataType = "String")
    @ApiOperation(value = "获取设备", notes = "根据id获取设备")
    @GetMapping("/{id}")
    public ResponseResult get(@PathVariable("id") String id) {
        return deviceService.getDeviceById(id);
    }

    /**
     * 分页查询设备（带条件）
     * @param dto 分页信息
     * @return 分页结果
     */
    @ApiImplicitParam(name = "dto", value = "分页信息", required = true, dataType = "PageRequestDto")
    @ApiOperation(value = "获取设备列表", notes = "根据条件来获取设备列表")
    @PostMapping("/list")
    public ResponseResult list(@RequestBody PageRequestDto dto) {

        return deviceService.conditionsList(dto);
    }

    @ApiImplicitParam(name = "dto", value = "分页信息", required = true, dataType = "PageRequestDto")
    @ApiOperation(value = "获取设备列表", notes = "根据管理员id来获取设备列表")
    @PostMapping("/list2")
    public ResponseResult getUserDeviceList(@RequestBody PageRequestDto dto) {
        log.info("获取用户设备列表{}", dto);

        return deviceService.getUserDeviceList(dto);
    }
}
