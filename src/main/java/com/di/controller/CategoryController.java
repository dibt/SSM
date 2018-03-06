package com.di.controller;

import com.di.global.ErrorCode;
import com.di.pojo.Category;
import com.di.service.ICategoryService;
import com.di.service.impl.CategoryServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    private static final Logger logger =Logger.getLogger(CategoryController.class);
    @Resource(name = "categoryService",type = CategoryServiceImpl.class)
    //@Autowired
    private ICategoryService categoryService;
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

    @RequestMapping("delete/{id}")
    @ResponseBody
    public Map<String,Object> delete(HttpServletRequest request, HttpServletResponse response,
                                  @PathVariable(name = "id") String id){
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
        try{
            categoryService.deleteAll();
        }catch (Exception e){
            logger.error("execution deleteall error:",e);
            return renderErrorDate(response, ErrorCode.CODE_SERVER_ERROR,"internal server error");
        }
            return renderErrorDate(response, ErrorCode.CODE_OK,ErrorCode.SUCCESS_KEY);
    }

    @RequestMapping(value = "edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> edit(HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam(value = "id") String id,
                                   @RequestParam(value = "name") String name){
        if(!stringToInteger(id)){
            return renderErrorDate(response,ErrorCode.CODE_REQ_PARAM_ERROR,"invalid params");
        }
        boolean status;
        Category category =new Category(Integer.parseInt(id),name);
        status=categoryService.edit(category);
        if(!status){
            return renderErrorDate(response, ErrorCode.CODE_SERVER_ERROR,"internal server error");
        }else{
            return renderErrorDate(response, ErrorCode.CODE_OK,ErrorCode.SUCCESS_KEY);
        }
    }

    @RequestMapping(value = "get")
    @ResponseBody
    public Map<String,Object> edit(HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam(value = "id") String id){
        if(!stringToInteger(id)){
            return renderErrorDate(response,ErrorCode.CODE_REQ_PARAM_ERROR,"invalid params");
        }
        Category category=categoryService.get(Integer.parseInt(id));
        if(category == null){
            return renderErrorDate(response, ErrorCode.CODE_SERVER_ERROR,"internal server error");
        }else{
            return renderDate(response,category);
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

    @RequestMapping(value = "page")
    @ResponseBody
    public Map<String,Object> page(HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam(value = "start") String start,
                                   @RequestParam(value = "count",defaultValue = "10",required = false )String count){
        if(!stringToInteger(start,count)){
            return renderErrorDate(response,ErrorCode.CODE_REQ_PARAM_ERROR,"invalid params");
        }
        List<Category> list=categoryService.page(Integer.parseInt(start),Integer.parseInt(count));
        if(list == null){
            return renderErrorDate(response, ErrorCode.CODE_SERVER_ERROR,"internal server error");
        }else if(list.size() ==0){
            return renderErrorDate(response,ErrorCode.CODE_EMPTY_RESULT,"result is empty");
        }else{
            return renderDate(response,list);
        }
    }

}
