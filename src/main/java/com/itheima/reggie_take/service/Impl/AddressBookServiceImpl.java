package com.itheima.reggie_take.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.itheima.reggie_take.entity.AddressBook;
import com.itheima.reggie_take.mapper.AddressBookMapper;
import com.itheima.reggie_take.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
