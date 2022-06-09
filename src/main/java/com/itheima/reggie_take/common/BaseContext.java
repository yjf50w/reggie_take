package com.itheima.reggie_take.common;

/**
 * @title: BaseContext
 * @Author Junfang Yuan
 * @Date: 2022/6/7 21:37
 * @Version 1.0
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }
    public static long getCurrentId(){
        return threadLocal.get();
    }
}
