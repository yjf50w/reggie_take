package com.itheima.reggie_take.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.itheima.reggie_take.entity.DishFlavor;
import com.itheima.reggie_take.mapper.DishFlavorMapper;
import com.itheima.reggie_take.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
