package com.itheima.reggie_take.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie_take.common.BaseContext;
import com.itheima.reggie_take.common.R;
import com.itheima.reggie_take.entity.Employee;
import com.itheima.reggie_take.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * @title: EmployeeController
 * @Author Junfang Yuan
 * @Date: 2022/6/7 11:46
 * @Version 1.0
 */
@RestController
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    /**
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername, employee.getUsername()).eq(Employee::getPassword, password);
        Employee emp = employeeService.getOne(wrapper);
        if (emp != null) {
            if (emp.getStatus() == 0) {
                return R.error("账号已经禁用");
            }
            request.getSession().setAttribute("employee", emp.getId());
            return R.success(emp);

        } else {
            return R.error("用户名或密码错误，登陆失败");
        }


    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {

        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8)));
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        Long empId = (Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);

        boolean save = employeeService.save(employee);
        if (save) {
            return R.success("添加成功");

        } else
            return R.error("添加失败");

    }

    @GetMapping("/page")
    public R<Page<Employee>> page(int page, int pageSize, String name) {
        log.info("page={},pageSize={},name={}", page, pageSize, name);
        Page<Employee> employeePage = new Page<>(page, pageSize);
//        employeeService.
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(name), Employee::getName, name).orderByDesc(Employee::getUpdateTime);
        employeeService.page(employeePage, wrapper);

        return R.success(employeePage);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
//        log.info(employee.toString());

//        employee.setUpdateUser(empId);
//        employee.setUpdateTime(LocalDateTime.now());


        log.info("update方法");
        employeeService.updateById(employee);
        return R.success("修改成功");


    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        if (employee != null) {

            return R.success(employee);
        }
        return R.error("没有查到对应员工信息");

    }




}
