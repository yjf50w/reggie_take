package com.itheima.reggie_take.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie_take.common.R;
import com.itheima.reggie_take.dto.SetmealDto;
import com.itheima.reggie_take.entity.Setmeal;

import java.util.List;


public interface SetmealService extends IService<Setmeal> {
    void saveWithDish(SetmealDto setmealDto);

    void deleteWithDish(List<Long> ids);

    SetmealDto getSetmealWithDish(Long id);

    void updateSetmealWithDish(SetmealDto setmealDto);
}
