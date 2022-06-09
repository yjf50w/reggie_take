package com.itheima.reggie_take.dto;


import com.itheima.reggie_take.entity.Setmeal;
import com.itheima.reggie_take.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
