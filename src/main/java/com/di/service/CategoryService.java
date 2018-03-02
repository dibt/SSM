package com.di.service;

import com.di.mapper.CategoryMapper;
import com.di.pojo.Category;
import com.di.util.Page;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
            logger.error(e.getMessage(),e);
            return new ArrayList<>();
        }
    }
    public List<Category> list(Page page) {
        // TODO Auto-generated method stub
        return categoryMapper.list(page);
    }

    public int total() {
        Integer total = null;
        try {
            total=categoryMapper.total();
            return total;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return total;
        }
    }

    public boolean deleteAll() {
        boolean status = true;
        try {
            List<Category> cs = list();
            for (Category c : cs) {
                categoryMapper.delete(c.getId());
            }
            return status;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return false;
        }
    }
    public boolean add(Category category) {
        boolean status=true;
        try{
            categoryMapper.add(category);
            return status;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return false;
        }
    }
    public boolean delete(Integer id){
        boolean status=true;
        try{
            categoryMapper.delete(id);
            return status;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return false;
        }
    }

}
