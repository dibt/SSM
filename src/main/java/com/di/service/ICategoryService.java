package com.di.service;

import com.di.pojo.Category;

import java.util.List;

/**
 * Created by bentengdi on 2018/3/6.
 */
public interface ICategoryService {
    List<Category> list();
    int total();
    void deleteAll() throws Exception;
    boolean add(Category category);
    boolean delete(Integer id);
    boolean edit(Category category);
    Category get(Integer id);
    List<Category> page(Integer start,Integer count);


}
