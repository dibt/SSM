package com.di.service.impl;

import com.di.service.IAspectService;
import org.springframework.stereotype.Service;

@Service
public class AspectServiceImpl implements IAspectService {
    public void method1(){
        System.out.println("this is AspectDemoService-method1");
    }
    public int method2(){
        System.out.println("this is AspectDemoService-method2");
        return 666;
    }
    public void method3() {
            System.out.println("this is AspectDemoService-method3");
            int i = 5/0;
    }
}
