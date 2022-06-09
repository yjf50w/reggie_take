package com.itheima.reggie_take.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.itheima.reggie_take.dto.DishDto;
import com.itheima.reggie_take.entity.Category;
import com.itheima.reggie_take.entity.Dish;
import com.itheima.reggie_take.entity.DishFlavor;
import com.itheima.reggie_take.mapper.DishFlavorMapper;
import com.itheima.reggie_take.mapper.DishMapper;
import com.itheima.reggie_take.service.CategoryService;
import com.itheima.reggie_take.service.DishFlavorService;
import com.itheima.reggie_take.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
//    @Autowired
//    DishMapper dishMapper;
//    @Autowired
//    DishFlavorMapper dishFlavorMapper;
    @Autowired
    DishFlavorService dishFlavorService;
    @Autowired
    CategoryService categoryService;


    /**
     * 新增菜品，并保存菜品口味信息
     * @param dishDto
     */
    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        save(dishDto);
        List<DishFlavor> flavors = dishDto.getFlavors();
        Long id = dishDto.getId();
        flavors.forEach((flavor)->flavor.setDishId(id));
        dishFlavorService.saveBatch(flavors);






    }

    @Override
    @Transactional
    public DishDto getByIdWithFlavor(Long id) {
        Dish dish = getById(id);
        Long categoryId = dish.getCategoryId();
        Category category = categoryService.getById(categoryId);
        String categoryName = category.getName();
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);
        dishDto.setCategoryName(categoryName);
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId, id);
        List<DishFlavor> list = dishFlavorService.list(wrapper);
        dishDto.setFlavors(list);
        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        updateById(dishDto);
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(wrapper);
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.forEach(flavor -> flavor.setDishId(dishDto.getId()));

        dishFlavorService.saveBatch(flavors);



    }
}
