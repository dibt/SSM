package com.di.controller;

import com.di.pojo.Category;
import com.di.service.CategoryService;
import com.di.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Created by bentengdi on 2017/11/17.
 */
@Controller
@RequestMapping("")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping("listCategory")
    //public ModelAndView listCategory(){
    public ModelAndView listCategory(Page page){
        ModelAndView mav = new ModelAndView();
        //List<Category> cs= categoryService.list();
        List<Category> cs= categoryService.list(page);
        //int total = categoryService.total();
        //page.caculateLast(total);
        

        // 放入转发参数
        mav.addObject("cs", cs);
        // 放入jsp路径
        mav.setViewName("listCategory");
        return mav;
    }
}
