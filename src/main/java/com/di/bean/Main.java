package com.di.bean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(PrePostConfig.class);
        BeanWayService beanWayService = context.getBean(BeanWayService.class);
        //BeanWayService beanWayService = (BeanWayService)context.getBean("beanWayService");
        //JSR250WayService jsr250WayService = context.getBean(JSR250WayService.class);
        //System.out.println("**********从Spring BeanFactory获取到的最终bean实例**********");
        //System.out.println("最终bean的值：" + beanWayService);
        context.close();
    }
}
