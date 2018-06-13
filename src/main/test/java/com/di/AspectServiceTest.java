package com.di;

import com.di.service.impl.AspectServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class AspectServiceTest {
    @Autowired
    AspectServiceImpl aspectService;
    @Test
    public void test() throws Exception{
       //aspectService.method1();
        //aspectService.method2();
        aspectService.method3();
    }
}
