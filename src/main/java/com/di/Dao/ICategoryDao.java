package com.di.dao;

import com.di.pojo.Category;
import com.di.util.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by bentengdi on 2017/11/17.
 */
public interface ICategoryDao {

    public void add(Category category);

    public void delete(int id);

    public Category get(int id);

    public void update(Category category);

    public List<Category> list();

    public List<Category> list(Page page);

    public List<Category> listpage(Page page);
    public int count();
    public int total();
}
