package com.itheima.reggie_take.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.itheima.reggie_take.entity.User;
import com.itheima.reggie_take.mapper.UserMapper;
import com.itheima.reggie_take.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
