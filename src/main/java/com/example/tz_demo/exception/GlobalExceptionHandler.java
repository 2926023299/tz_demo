package com.example.tz_demo.exception;

import com.example.tz_demo.entity.common.AppHttpCodeEnum;
import com.example.tz_demo.entity.common.R;
import com.example.tz_demo.entity.common.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public R<String> handleException(HttpServletRequest request, SQLIntegrityConstraintViolationException e) {
        log.error("数据库异常: {}", e.getMessage());

        if (e.getMessage().contains("Duplicate entry")) {
            String[] split = e.getMessage().split(" ");

            return R.error(split[2] + "已存在");
        }

        return R.error("数据库异常");
    }

    @ExceptionHandler(value = CustomException.class)
    public ResponseResult handleException(CustomException e) {
        log.error(e.getMessage());

        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseResult handleException(Exception e) {
        e.printStackTrace();
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
    }
}
