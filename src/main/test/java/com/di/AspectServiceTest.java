package com.di;

import com.di.service.AspectDemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class AspectServiceTest {
    @Autowired
     AspectDemoService aspectDemoService;
    @Test
    public void test() throws Exception{
       //aspectDemoService.method1();
        //aspectDemoService.method2();
        aspectDemoService.method3();
    }
}
