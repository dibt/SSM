package com.di.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class BeanPostProcessorImpl implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanname) throws BeansException {
        System.out.println("针对Spring上下文中所有的bean实例化之前执行"+beanname);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanname) throws BeansException {
        System.out.println("针对Spring上下文中所有的bean实例化完成之后执行"+beanname);
        return bean;
    }
}
