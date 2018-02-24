package com.di.service;

import com.di.mapper.CategoryMapper;
import com.di.pojo.Category;
import com.di.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by bentengdi on 2017/11/17.
 */
@Service
public class CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    public List<Category> list(){
        return categoryMapper.list();
    }
    public List<Category> list(Page page) {
        // TODO Auto-generated method stub
        return categoryMapper.list(page);
    }

    public int total() {
        return categoryMapper.total();
    }

    public void deleteAll() {
        List<Category> cs = list();
        for (Category c : cs) {

            categoryMapper.delete(c.getId());
        }
    }
    @Transactional(propagation=Propagation.REQUIRED,rollbackForClassName="Exception")
    public void addTwo() {
        Category c1 = new Category();
        c1.setId(0);
        c1.setName("短的名字");
        categoryMapper.add(c1);

        Category c2 = new Category();
        c2.setId(1);
        c2.setName("名字长对应字段放不下,名字长对应字段放不下,名字长对应字段放不下,名字长对应字段放不下,名字长对应字段放不下,名字长对应字段放不下,名字长对应字段放不下,名字长对应字段放不下,");
        categoryMapper.add(c2);
    };

}
