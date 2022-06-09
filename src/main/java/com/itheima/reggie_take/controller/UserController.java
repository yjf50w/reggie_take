package com.itheima.reggie_take.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.itheima.reggie_take.common.BaseContext;
import com.itheima.reggie_take.common.R;
import com.itheima.reggie_take.entity.User;
import com.itheima.reggie_take.service.UserService;
import com.itheima.reggie_take.utils.SMSUtils;
import com.itheima.reggie_take.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @title: UserController
 * @Author Junfang Yuan
 * @Date: 2022/6/8 23:06
 * @Version 1.0
 *
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 发送手机验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)) {
            String code =  ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}",code);
//            SMSUtils.sendMessage("瑞吉外卖","",phone,code);
            session.setAttribute(phone, code);
            session.setMaxInactiveInterval(600);
            return R.success("短信发送成功");
        }
        return R.error("短信发送失败");



    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {
        log.info(map.toString());
        String phone = map.get("phone").toString();
        String code = map.get("code").toString();
        String realCode= (String) session.getAttribute(phone);
        if (realCode != null && realCode.equals(code) ) {
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getPhone, phone);
            User user = userService.getOne(wrapper);
            if (user==null){
                 user = new User();
                 user.setPhone(phone);
                userService.save(user);
            }
            session.setAttribute("user", user.getId());
            return R.success(user);
        }
        return R.error("登录失败");
    }
}
