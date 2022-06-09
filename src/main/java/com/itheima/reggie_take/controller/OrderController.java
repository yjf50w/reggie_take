package com.itheima.reggie_take.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie_take.common.BaseContext;
import com.itheima.reggie_take.common.R;
import com.itheima.reggie_take.entity.Orders;
import com.itheima.reggie_take.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @title: OrderController
 * @Author Junfang Yuan
 * @Date: 2022/6/9 17:48
 * @Version 1.0
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        orderService.submit(orders);
        return R.success("订单已提交，等待付款");



    }
    @GetMapping("/userPage")
    public R<Page<Orders>> page(int page,int pageSize){
        long userId = BaseContext.getCurrentId();
        Page<Orders> ordersPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getUserId, userId).orderByDesc(Orders::getCheckoutTime);
        orderService.page(ordersPage, wrapper);
        return R.success(ordersPage);

    }
}
