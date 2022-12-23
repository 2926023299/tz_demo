package com.example.tz_demo.dto;

import com.example.tz_demo.entity.Device;
import com.example.tz_demo.entity.ManageUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ApiModel(value = "用户设备信息")
public class UserDeviceDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private String UserId;

    @ApiModelProperty(value = "设备id")
    private String DeviceId;

    @ApiModelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "设备类型")
    private Integer Type;

    @ApiModelProperty(value = "设备金额")
    private Integer purchaseAmount;

    @ApiModelProperty(value = "创建用户")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "构造方法")
    public static UserDeviceDto init(ManageUser manageUser, Device device) {
        UserDeviceDto userDeviceDto = new UserDeviceDto();
        BeanUtils.copyProperties(device, userDeviceDto);
        userDeviceDto.setUserId(manageUser.getId());
        userDeviceDto.setDeviceId(device.getId());

        return userDeviceDto;
    }
}
