package com.itheima.reggie_take.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie_take.common.CustomException;
import com.itheima.reggie_take.entity.Category;
import com.itheima.reggie_take.entity.Dish;
import com.itheima.reggie_take.entity.Employee;
import com.itheima.reggie_take.entity.Setmeal;
import com.itheima.reggie_take.mapper.CategoryMapper;
import com.itheima.reggie_take.mapper.DishMapper;
import com.itheima.reggie_take.mapper.EmployeeMapper;
import com.itheima.reggie_take.mapper.SetmealMapper;
import com.itheima.reggie_take.service.CategoryService;
import com.itheima.reggie_take.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @title: EmployeeServiceImpl
 * @Author Junfang Yuan
 * @Date: 2022/6/7 11:42
 * @Version 1.0
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    DishMapper dishMapper;
    @Autowired
    SetmealMapper setmealMapper;
    @Autowired
    CategoryMapper categoryMapper;
    /**
     * 根据id删除，先判断是否包含菜品或套餐
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishWrapper = new LambdaQueryWrapper<>();
        dishWrapper.eq(Dish::getCategoryId, id);
        Long count = dishMapper.selectCount(dishWrapper);

        if (count>0){
            throw new CustomException("当前分类下关联了其他菜品，不能删除");
        }
        LambdaQueryWrapper<Setmeal> setmealWrapper = new LambdaQueryWrapper<>();
        setmealWrapper.eq(Setmeal::getCategoryId, id);
        Long count1 = setmealMapper.selectCount(setmealWrapper);
        if (count1>0){
            throw new CustomException("当前分类下关联了其他套餐，不能删除");
        }

        categoryMapper.deleteById(id);


    }
}
