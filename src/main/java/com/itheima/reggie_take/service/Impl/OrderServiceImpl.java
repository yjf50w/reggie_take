package com.itheima.reggie_take.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie_take.common.BaseContext;
import com.itheima.reggie_take.entity.*;
import com.itheima.reggie_take.mapper.*;
import com.itheima.reggie_take.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @title: OrderServiceImpl
 * @Author Junfang Yuan
 * @Date: 2022/6/9 17:55
 * @Version 1.0
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {
    @Autowired
    AddressBookMapper addressBookMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    ShoppingCartMapper shoppingCartMapper;
    @Autowired
    OrderDetailMapper orderDetailMapper;




    @Override
    @Transactional
    public void submit(@RequestBody Orders orders) {
        //拼装order并插入orders表中
        long userId = BaseContext.getCurrentId();
        orders.setUserId(userId);
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookMapper.selectById(addressBookId);
        String phone = addressBook.getPhone();
        String provinceName = addressBook.getProvinceName();
        String cityName = addressBook.getCityName();
        String districtName = addressBook.getDistrictName();
        String detail = addressBook.getDetail();
        String address=provinceName+cityName+districtName+detail;
        String consignee = addressBook.getConsignee();
        orders.setPhone(phone);
        orders.setAddress(address);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        User user = userMapper.selectById(userId);
        String userName = user.getName();
        orders.setUserName(userName);
        orders.setConsignee(consignee);
        orders.setStatus(1);
        orders.setNumber(String.valueOf(IdWorker.getId()));


//      查询购物车数据,转化成订单详情并插入订单详情表
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, userId);
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.selectList(wrapper);
        //遍历购物车信息获取总价
        AtomicInteger amount = new AtomicInteger(0);
        List<OrderDetail> orderDetailList = shoppingCarts.stream().map(shoppingCart -> {
            OrderDetail orderDetail = new OrderDetail();
            BigDecimal perPrice = shoppingCart.getAmount();
            Integer number = shoppingCart.getNumber();
            amount.addAndGet(perPrice.multiply(new BigDecimal(number)).intValue());
            BeanUtils.copyProperties(shoppingCart, orderDetail, "userId");
            orderDetail.setOrderId(orders.getId());
            return orderDetail;
        }).collect(Collectors.toList());
        orders.setAmount(new BigDecimal(amount.get()));
        orderMapper.insert(orders);
//        BeanUtils.copyProperties(shoppingCart,orderDetail,"userId");
        orderDetailList.forEach(orderDetail -> {
            orderDetail.setOrderId(orders.getId());
            orderDetailMapper.insert(orderDetail);
        });

//        清空购物车数据

        shoppingCartMapper.delete(wrapper);



    }
}
