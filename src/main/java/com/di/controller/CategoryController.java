package com.di.controller;

import com.di.global.ErrorCode;
import com.di.pojo.Category;
import com.di.service.CategoryService;
import com.di.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by bentengdi on 2017/11/17.
 */
@Controller
@RequestMapping("category")
public class CategoryController extends BaseController{
    @Resource(name = "categoryService",type =CategoryService.class )
    private CategoryService categoryService;
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> listCategory(HttpServletRequest request, HttpServletResponse response){
        List<Category> list=categoryService.list();
        if(list == null){
            return renderErrorDate(response, ErrorCode.CODE_SERVER_ERROR,"internal server error");
        }else{
            return renderDate(response,list);
        }
    }

    @RequestMapping("add")
    @ResponseBody
    public Map<String,Object> add(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam(name = "id",required = true) String id,
                                  @RequestParam(name = "name") String name){
        if(!stringToInteger(id)){
            return renderErrorDate(response,ErrorCode.CODE_REQ_PARAM_ERROR,"invalid params");
        }
        boolean status;
        Category category =new Category(Integer.parseInt(id),name);
        status=categoryService.add(category);
        if(!status){
            return renderErrorDate(response, ErrorCode.CODE_SERVER_ERROR,"internal server error");
        }else{
            return renderErrorDate(response, ErrorCode.CODE_OK,ErrorCode.SUCCESS_KEY);
        }
    }

    @RequestMapping("delete")
    @ResponseBody
    public Map<String,Object> delete(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam(name = "id") String id){
        if(!stringToInteger(id)){
            return renderErrorDate(response,ErrorCode.CODE_REQ_PARAM_ERROR,"invalid params");
        }
        boolean status;
        status=categoryService.delete(Integer.parseInt(id));
        if(!status){
            return renderErrorDate(response, ErrorCode.CODE_SERVER_ERROR,"internal server error");
        }else{
            return renderErrorDate(response, ErrorCode.CODE_OK,ErrorCode.SUCCESS_KEY);
        }
    }

    @RequestMapping("deleteall")
    @ResponseBody
    public Map<String,Object> deleteAll(HttpServletRequest request, HttpServletResponse response){
        boolean status;
        status=categoryService.deleteAll();
        if(!status){
            return renderErrorDate(response, ErrorCode.CODE_SERVER_ERROR,"internal server error");
        }else{
            return renderErrorDate(response, ErrorCode.CODE_OK,ErrorCode.SUCCESS_KEY);
        }
    }
    @RequestMapping("total")
    @ResponseBody
    public Map<String,Object> total(HttpServletRequest request, HttpServletResponse response){
        Integer total=categoryService.total();
        if(total == null){
            return renderErrorDate(response, ErrorCode.CODE_SERVER_ERROR,"internal server error");
        }else{
            return renderDate(response, total);
        }
    }
}
