package com.itheima.reggie_take;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
@SpringBootApplication
@MapperScan("com/itheima/reggie_take/mapper")
public class ReggieTakeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReggieTakeApplication.class, args);
    }

}
