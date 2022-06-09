package com.itheima.reggie_take.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie_take.common.R;
import com.itheima.reggie_take.entity.Category;
import com.itheima.reggie_take.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @title: CategoryController
 * @Author Junfang Yuan
 * @Date: 2022/6/7 22:16
 * @Version 1.0
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping()
    public R<String> save(@RequestBody Category category) {
        boolean save = categoryService.save(category);
        if (save) {

            return R.success("添加成功");
        }
        return R.error("添加失败");


    }
    @GetMapping("/page")
    public R<Page<Category>> page(int page,int pageSize){
        Page<Category> categoryPage = new Page<>(page,pageSize);
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Category::getSort);
        categoryPage=categoryService.page(categoryPage, wrapper);
        return R.success(categoryPage);

    }
    @DeleteMapping
    public R<String> delete(Long id){
        categoryService.remove(id);
        return R.success("删除成功");

    }

    @PutMapping
    public R<String> update(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("修改成功");

    }

    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(category.getType() != null, Category::getType, category.getType()).
                orderByAsc(Category::getType).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(wrapper);
        return R.success(list);

    }


}
