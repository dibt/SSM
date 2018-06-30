package com.di.bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//使用JSR250形式的Bean
public class JSR250WayService {
    //构造函数执行完之后执行
    @PostConstruct
    public void inti(){
        System.out.println("jsr250-init-method");
    }
    public JSR250WayService(){
        super();
        System.out.println("初始化构造函数-JSR250WayService");
    }
    //在Bean销毁之前执行
    @PreDestroy
    public void destory(){
        System.out.println("jsr250-destory-method");
    }

}
