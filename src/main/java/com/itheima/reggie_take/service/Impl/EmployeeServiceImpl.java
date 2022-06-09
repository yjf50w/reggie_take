package com.itheima.reggie_take.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie_take.entity.Employee;
import com.itheima.reggie_take.mapper.EmployeeMapper;
import com.itheima.reggie_take.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @title: EmployeeServiceImpl
 * @Author Junfang Yuan
 * @Date: 2022/6/7 11:42
 * @Version 1.0
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
