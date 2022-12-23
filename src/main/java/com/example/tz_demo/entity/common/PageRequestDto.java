package com.example.tz_demo.entity.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@ApiModel(value = "分页请求参数DTO")
public class PageRequestDto<T> {

    @ApiModelProperty(value = "当前页码")
    protected Integer size;

    @ApiModelProperty(value = "每页条数")
    protected Integer page;

    @ApiModelProperty(value = "查询条件")
    protected T data;

    public void checkParam() {
        if (this.page == null || this.page < 0) {
            setPage(1);
        }
        if (this.size == null || this.size < 0 || this.size > 100) {
            setSize(10);
        }
    }
}
