package com.itheima.reggie_take.common;

/**
 * @title: CustomException
 * @Author Junfang Yuan
 * @Date: 2022/6/7 23:17
 * @Version 1.0
 */
public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
