package com.di.mapper;

import com.di.pojo.Category;
import com.di.util.Page;

import java.util.List;

/**
 * Created by bentengdi on 2017/11/17.
 */
public interface CategoryMapper {
    public void add(Category category);

    public void delete(int id);

    public Category get(int id);

    public void update(Category category);

    public List<Category> list();

    public int count();

    public List<Category> listtop(Page page);

    public int total();
}
