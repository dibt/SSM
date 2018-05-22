package com.di.service;

import com.di.pojo.Category;

import java.util.List;

/**
 * Created by bentengdi on 2018/5/21.
 */
public interface IHttpRequestService {
     List<Category> getCategoryByPage(Integer start,Integer count);
}
