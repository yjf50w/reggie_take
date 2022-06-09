package com.itheima.reggie_take.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie_take.common.R;
import com.itheima.reggie_take.dto.DishDto;
import com.itheima.reggie_take.entity.Category;
import com.itheima.reggie_take.entity.Dish;
import com.itheima.reggie_take.entity.DishFlavor;

import com.itheima.reggie_take.service.CategoryService;
import com.itheima.reggie_take.service.DishFlavorService;
import com.itheima.reggie_take.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @title: DishController
 * @Author Junfang Yuan
 * @Date: 2022/6/8 14:06
 * @Version 1.0
 */
@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {
    @Autowired
    DishService dishService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    DishFlavorService dishFlavorService;


    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);

        return R.success("添加菜品成功");

    }

    @GetMapping("/page")
    public R<Page<DishDto>> page(int page, int pageSize, String name) {
        Page<Dish> dishPage = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Dish::getPrice).like(name != null, Dish::getName, name);
        dishService.page(dishPage, wrapper);
        BeanUtils.copyProperties(dishPage, dishDtoPage, "records");
//foreach方式
        List<DishDto> list = new ArrayList<>();
        for (Dish record : dishPage.getRecords()) {
            DishDto dishDto = new DishDto();
            Long categoryId = record.getCategoryId();
            Category category = categoryService.getById(categoryId);
            dishDto.setCategoryName(category.getName());
            BeanUtils.copyProperties(record, dishDto);
            list.add(dishDto);
        }
        dishDtoPage.setRecords(list);

//        stream方式
//        List<Dish> records = dishPage.getRecords();
//        dishDtoPage.setRecords(records.stream().map((dish) -> {
//            DishDto dishDto = new DishDto();
//            BeanUtils.copyProperties(dish, dishDto);
//            Long categoryId = dish.getCategoryId();
//            Category category = categoryService.getById(categoryId);
//            String categoryName = category.getName();
//            dishDto.setCategoryName(categoryName);
//            return dishDto;
//        }).collect(Collectors.toList()));

        return R.success(dishDtoPage);

    }

    @GetMapping("/{id}")
    public R<DishDto> getById(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);


        return R.success(dishDto);


    }

    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());
        dishService.updateWithFlavor(dishDto);

        return R.success("添加菜品成功");

    }

    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable("status") Integer status, Long[] ids) {
        LambdaUpdateWrapper<Dish> wrapper = new LambdaUpdateWrapper<>();
        wrapper.in(ids != null, Dish::getId, ids).set(Dish::getStatus, status);

        dishService.update(wrapper);

        if (status == 1) {
            return R.success("启售成功");
        } else {
            return R.success("停售成功");
        }
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        boolean b = dishService.removeBatchByIds(ids);
//        dishService.removeById(ids);

        return R.success("删除成功");

    }

    //    @GetMapping("/list")
//    public R<List<Dish>> list(Long categoryId){
//
//        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(categoryId!=null,Dish::getCategoryId, categoryId)
//                .eq(Dish::getStatus,1);
//        List<Dish> list = dishService.list(wrapper);
//        return R.success(list);
//
//    }
    @GetMapping("/list")
    public R<List<DishDto>> list(Long categoryId) {

        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(categoryId != null, Dish::getCategoryId, categoryId)
                .eq(Dish::getStatus, 1);
        List<Dish> dishList = dishService.list(wrapper);

        List<DishDto> dishDtoList = dishList.stream().map(dish -> {
            DishDto dishDto = new DishDto();
            Long dishId = dish.getId();
            LambdaQueryWrapper<DishFlavor> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(dishId != null, DishFlavor::getDishId, dishId);
            List<DishFlavor> flavorList = dishFlavorService.list(wrapper1);
            dishDto.setFlavors(flavorList);
            BeanUtils.copyProperties(dish, dishDto);
            return dishDto;
        }).collect(Collectors.toList());

        return R.success(dishDtoList);

    }
}
