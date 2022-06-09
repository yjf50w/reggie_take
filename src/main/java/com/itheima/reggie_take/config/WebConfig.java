package com.itheima.reggie_take.config;

import com.itheima.reggie_take.common.JacksonObjectMapper;
import com.itheima.reggie_take.interceptor.LoginInterceptor;
import com.itheima.reggie_take.interceptor.UserLoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @title: WebConfig
 * @Author Junfang Yuan
 * @Date: 2022/6/7 15:32
 * @Version 1.0
 */
@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).
                addPathPatterns("/backend/page/**","/backend/index.html").
                excludePathPatterns("/backend/page/login/login.html");
        registry.addInterceptor(new UserLoginInterceptor()).
                addPathPatterns("/front/page/**","/front/index.html","/addressBook/**","/shoppingCart/**","/order/**")
                .excludePathPatterns("/front/page/login.html");
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        log.info("拓展消息转化器");
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        converters.add(0, messageConverter);
    }
}
