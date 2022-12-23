package com.example.tz_demo.entity;


import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("device")
@ApiModel(description = "设备实体")
public class Device implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 用户名
     */
    @TableField("name")
    @ApiModelProperty(value = "name")
    private String name;

    /**
     * 类型
     */
    @TableField("type")
    @ApiModelProperty(value = "type")
    private Integer type;

    /**
     * 购置金额
     */
    @TableField("purchase_amount")
    @ApiModelProperty(value = "purchase_amount")
    private Integer purchaseAmount;

    /**
     * 创建用户
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "create_user")
    private String createUser;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "update_time")
    private LocalDateTime updateTime;
}

