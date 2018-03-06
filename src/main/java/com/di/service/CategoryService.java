package com.di.service;

import com.di.mapper.CategoryMapper;
import com.di.pojo.Category;
import com.di.util.Page;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bentengdi on 2017/11/17.
 */
@Service
public class CategoryService {
    private static final Logger logger = Logger.getLogger(CategoryMapper.class);
    @Autowired
    CategoryMapper categoryMapper;
    public List<Category> list(){
        try {
            List<Category> list =categoryMapper.list();
            return  list;
        }catch(Exception e) {
            logger.error("get Category list error:",e);
            return new ArrayList<>();
        }
    }

    public int total() {
        Integer total = null;
        try {
            total=categoryMapper.total();
            return total;
        }catch (Exception e){
            logger.error("get Category total error:",e);
            return total;
        }
    }
    public void deleteAll() throws Exception{
            List<Category> cs = list();
            for (Category c : cs) {
                categoryMapper.delete(c.getId());
            }
    }
    public boolean add(Category category) {
        boolean status=true;
        try{
            categoryMapper.add(category);
            return status;
        }catch (Exception e){
            logger.error("add Category error:",e);
            return false;
        }
    }
    public boolean delete(Integer id){
        boolean status=true;
        try{
            categoryMapper.delete(id);
            return status;
        }catch (Exception e){
            logger.error("delete Category error:",e);
            return false;
        }
    }

    public boolean edit(Category category){
        boolean status=true;
        try{
            categoryMapper.update(category);
            return status;
        }catch (Exception e){
            logger.error("update Category error:",e);
            return false;
        }
    }

    public Category get(Integer id){
        try{
            Category category=categoryMapper.get(id);
            return category;
        }catch (Exception e){
            logger.error("get Category error:",e);
            return null;
        }
    }

    public List<Category> page(Integer start,Integer count){
        Page page=new Page();
        page.setStart(start);
        page.setCount(count);
        try{
            List<Category> list=categoryMapper.listpage(page);
            return list;
        }catch (Exception e){
            logger.error("get Category pagelist error:",e);
            return null;
        }
    }

}
