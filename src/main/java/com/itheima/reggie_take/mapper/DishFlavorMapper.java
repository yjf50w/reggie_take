package com.itheima.reggie_take.mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie_take.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

//@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> { void deleteById();

    
}
