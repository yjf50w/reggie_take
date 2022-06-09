package com.itheima.reggie_take.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie_take.dto.DishDto;
import com.itheima.reggie_take.entity.Dish;


public interface DishService extends IService<Dish> {
     void saveWithFlavor(DishDto dishDto);

     DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);
}
