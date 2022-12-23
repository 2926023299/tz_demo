package com.example.tz_demo.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * author:  唐哲
 * date:    2022/12/22 14:23
 * description: 元数据自动填充
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入时填充数据
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("insertFill");
        this.setFieldValByName("createUser", BaseContext.getUser().getId(), metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }

    /**
     * 更新时填充数据
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("updateFill");
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }
}