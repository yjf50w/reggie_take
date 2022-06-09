package com.itheima.reggie_take.dto;


import com.itheima.reggie_take.entity.Dish;
import com.itheima.reggie_take.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
