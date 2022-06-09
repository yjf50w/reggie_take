package com.itheima.reggie_take.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.itheima.reggie_take.entity.ShoppingCart;
import com.itheima.reggie_take.mapper.ShoppingCartMapper;
import com.itheima.reggie_take.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

}
