package com.itheima.reggie_take.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @title: MyMetaObjectHandler
 * @Author Junfang Yuan
 * @Date: 2022/6/7 21:10
 * @Version 1.0
 */

/**
 * 自定义元数据对象处理器
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Autowired
    HttpServletRequest request;

    @Override
    public void insertFill(MetaObject metaObject) {
//      log.info("填充"+metaObject.toString());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        Long empId=BaseContext.getCurrentId();
//        Long empId = (Long) request.getSession().getAttribute("employee");
        metaObject.setValue("createUser", empId);
        metaObject.setValue("updateUser", empId);
        //        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8)));
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        Long empId = (Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("填充"+metaObject.toString());
        Long empId=BaseContext.getCurrentId();
//        Long empId = (Long) request.getSession().getAttribute("employee");

        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", empId);


    }
}
