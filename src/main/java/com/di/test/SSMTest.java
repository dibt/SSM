package com.di.test;

import com.di.mapper.CategoryMapper;
import com.di.pojo.Category;
import com.di.service.CategoryService;
import com.di.util.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by bentengdi on 2017/11/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SSMTest {
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    CategoryService categoryService;
    @Test
    public void testList() {
            //categoryService.deleteAll();
            //categoryService.addTwo();
        Page p = new Page();
        p.setStart(2);
        p.setCount(30);
        List<Category> cs=categoryMapper.list(p);
        for (Category c : cs) {
            System.out.println(c.getName());
        }
    }
}
