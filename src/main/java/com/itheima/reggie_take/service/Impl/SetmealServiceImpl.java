package com.itheima.reggie_take.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.itheima.reggie_take.common.R;
import com.itheima.reggie_take.dto.SetmealDto;
import com.itheima.reggie_take.entity.Setmeal;
import com.itheima.reggie_take.entity.SetmealDish;
import com.itheima.reggie_take.mapper.SetmealMapper;
import com.itheima.reggie_take.service.SetmealDishService;
import com.itheima.reggie_take.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    SetmealDishService setmealDishService;
    @Transactional
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        Long id = setmealDto.getId();
        setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(id));
        setmealDishService.saveBatch(setmealDishes);

    }

    @Override
    @Transactional
    public void deleteWithDish(List<Long> ids) {
        removeBatchByIds(ids);

        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(wrapper);
    }

    @Override
    @Transactional
    public SetmealDto getSetmealWithDish(Long id) {
        Setmeal setmeal = getById(id);
        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId, id);
        List<SetmealDish> list = setmealDishService.list(wrapper);
        SetmealDto setmealDto = new SetmealDto();
        setmealDto.setSetmealDishes(list);
        BeanUtils.copyProperties(setmeal, setmealDto);
        return setmealDto;
    }

    @Override
    @Transactional
    public void updateSetmealWithDish(SetmealDto setmealDto) {
        updateById(setmealDto);
        LambdaUpdateWrapper<SetmealDish> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(setmealDto.getId() != null, SetmealDish::getSetmealId, setmealDto.getId());
        setmealDishService.remove(wrapper);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setmealDto.getId()));

        setmealDishService.saveBatch(setmealDishes);

    }


}
