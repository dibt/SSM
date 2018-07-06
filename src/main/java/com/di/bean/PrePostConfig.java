package com.di.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.di.bean")
public class PrePostConfig {
    //@Bean返回值是一个Bean，Bean的名称是方法名
    @Bean
    BeanPostProcessorImpl postProcessor(){
        return new BeanPostProcessorImpl();
    }
    @Bean(initMethod = "init",destroyMethod = "destory")
    BeanWayService beanWayService(){
        BeanWayService beanWayService = new BeanWayService();
        beanWayService.setId(2);
        beanWayService.setName("第二步");
        return  beanWayService;
    }
//    @Bean
//    JSR250WayService jsr250WayService(){
//        return new JSR250WayService();
//    }
}
