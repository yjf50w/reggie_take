package com.itheima.reggie_take.controller;

import com.itheima.reggie_take.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @title: orderDetailController
 * @Author Junfang Yuan
 * @Date: 2022/6/9 17:48
 * @Version 1.0
 */
@RestController
@RequestMapping("/orderDetail")
@Slf4j
public class orderDetailController {
    @Autowired
    OrderDetailService orderDetailService;
}
