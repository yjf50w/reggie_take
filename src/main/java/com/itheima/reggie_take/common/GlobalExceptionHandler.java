package com.itheima.reggie_take.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

/**
 * @title: GlobalExceptionHandler
 * @Author Junfang Yuan
 * @Date: 2022/6/7 16:46
 * @Version 1.0
 */
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLException.class)
    public R<String> exceptionHandler(SQLException exception){

        log.error(exception.getMessage());
        if (exception.getMessage().contains("Duplicate entry")){
            String[] split = exception.getMessage().split(" ");
            String msg = split[2]+"已经存在";
            return R.error(msg);
        }
        return R.error("未知错误");

    }

    @ExceptionHandler(CustomException.class)
    public R<String> customException(CustomException exception){
        return R.error(exception.getMessage());

    }


}
