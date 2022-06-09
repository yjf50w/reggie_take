package com.itheima.reggie_take.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie_take.entity.OrderDetail;
import com.itheima.reggie_take.mapper.OrderDetailMapper;
import com.itheima.reggie_take.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}