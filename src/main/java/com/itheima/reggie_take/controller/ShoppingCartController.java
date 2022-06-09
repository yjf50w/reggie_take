package com.itheima.reggie_take.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.itheima.reggie_take.common.BaseContext;
import com.itheima.reggie_take.common.R;
import com.itheima.reggie_take.entity.ShoppingCart;
import com.itheima.reggie_take.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @title: ShoppingCartController
 * @Author Junfang Yuan
 * @Date: 2022/6/9 14:30
 * @Version 1.0
 */
@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
//        log.info(shoppingCart.toString());
        long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, userId);

        if (dishId!=null){
            wrapper.eq(ShoppingCart::getDishId, dishId);


        }else {
            wrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());

        }
        ShoppingCart dishOrSetmeal = shoppingCartService.getOne(wrapper);
        if (dishOrSetmeal!=null){
            Integer originNumber = dishOrSetmeal.getNumber();
//            BigDecimal originAmount = dishOrSetmeal.getAmount();
            dishOrSetmeal.setNumber(originNumber+1);
//            Integer newNumber = dishOrSetmeal.getNumber();
//            dishOrSetmeal.setAmount(originAmount);

            shoppingCartService.updateById(dishOrSetmeal);
        }else {
            shoppingCart.setNumber(1);
            shoppingCartService.save(shoppingCart);
            dishOrSetmeal=shoppingCart;


        }
        dishOrSetmeal.setCreateTime(LocalDateTime.now());



        return R.success(dishOrSetmeal);

    }
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        long userId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, userId).orderByDesc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(wrapper);
        return R.success(list);
    }

    @PostMapping("/sub")
    public R<String> sub(@RequestBody ShoppingCart shoppingCart){
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> dishOrSetmealWrapper = new LambdaQueryWrapper<>();
        dishOrSetmealWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());

        if (dishId!=null){

            dishOrSetmealWrapper.eq(ShoppingCart::getDishId, dishId);
        }else {
            dishOrSetmealWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }
        ShoppingCart dishOrSetmeal = shoppingCartService.getOne(dishOrSetmealWrapper);

        Integer number = dishOrSetmeal.getNumber();
        dishOrSetmeal.setNumber(number - 1);
        shoppingCartService.updateById(dishOrSetmeal);
        return R.success("减少成功");


    }
    @DeleteMapping("/clean")
    public  R<String> delete(){
        long userId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, userId);
        shoppingCartService.remove(wrapper);
        return R.success("清空成功");
    }

}
