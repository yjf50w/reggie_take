package com.itheima.reggie_take.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie_take.common.R;
import com.itheima.reggie_take.dto.SetmealDto;
import com.itheima.reggie_take.entity.Category;
import com.itheima.reggie_take.entity.Setmeal;
import com.itheima.reggie_take.mapper.SetmealDishMapper;
import com.itheima.reggie_take.mapper.SetmealMapper;
import com.itheima.reggie_take.service.CategoryService;
import com.itheima.reggie_take.service.SetmealDishService;
import com.itheima.reggie_take.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @title: SetMealController
 * @Author Junfang Yuan
 * @Date: 2022/6/8 19:04
 * @Version 1.0
 */
@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetMealController {
    @Autowired
    SetmealService setmealService;
    @Autowired
    SetmealDishService setmealDishService;
    @Autowired
    CategoryService categoryService;

    /**
     * 新增套餐
     *
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
//        log.info(setmealDto.toString());
//        log.info(setmealDto.getCategoryId().toString());
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");

    }

    @GetMapping("/page")
    public R<Page<SetmealDto>> page(int page, int pageSize, String name) {
        Page<SetmealDto> setmealDtoPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null, Setmeal::getName, name);
        Page<Setmeal> setmealPage = new Page<>(page, pageSize);
        setmealService.page(setmealPage, wrapper);
        BeanUtils.copyProperties(setmealPage, setmealDtoPage, "records");
        List<Setmeal> records = setmealPage.getRecords();
//        List<SetmealDto> list=new ArrayList<>();
//        for (Setmeal record : records) {
//            SetmealDto setmealDto = new SetmealDto();
//            BeanUtils.copyProperties(record, setmealDto);
//            Long categoryId = record.getCategoryId();
//            Category category = categoryService.getById(categoryId);
//            String categoryName = category.getName();
//            setmealDto.setCategoryName(categoryName);
//            list.add(setmealDto);
//
//        }
//        setmealDtoPage.setRecords(list);
//        return R.success(setmealDtoPage);


        List<SetmealDto> list = records.stream().map(record -> {
            Long categoryId = record.getCategoryId();
            Category category = categoryService.getById(categoryId);
            String categoryName = category.getName();
            SetmealDto setmealDto = new SetmealDto();
            setmealDto.setCategoryName(categoryName);
            BeanUtils.copyProperties(record, setmealDto);
            return setmealDto;

        }).collect(Collectors.toList());
        setmealDtoPage.setRecords(list);
        return R.success(setmealDtoPage);
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        setmealService.deleteWithDish(ids);
        return R.success("删除成功");
    }

    @GetMapping("/{id}")
    public R<SetmealDto> getSetmealByIdWithDish(@PathVariable Long id) {
        SetmealDto setmealWithDish = setmealService.getSetmealWithDish(id);
        return R.success(setmealWithDish);
    }

    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto){

        setmealService.updateSetmealWithDish(setmealDto);
        return R.success("修改成功");


    }
    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable("status") Integer status,@RequestParam List<Long> ids){
        LambdaUpdateWrapper<Setmeal> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(status!=null,Setmeal::getStatus,status).in(ids!=null, Setmeal::getId,ids);
        setmealService.update(wrapper);
        return R.success("修改成功");


    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(@RequestParam Long categoryId){
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(categoryId != null, Setmeal::getCategoryId, categoryId);
        List<Setmeal> list = setmealService.list(wrapper);
        return R.success(list);

    }

}
