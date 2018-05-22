package com.di.controller;


import com.di.global.Code;
import com.di.pojo.Category;
import com.di.service.IHttpRequestService;
import com.di.service.impl.HttpRequestServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by bentengdi on 2018/5/22.
 */
@Controller
@RequestMapping("http")
public class HttpRequestController extends BaseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestController.class);
    @Autowired
    IHttpRequestService httpRequestService;
    @RequestMapping("/request")
    @ResponseBody
    public Map<String,Object> getCategoryByPage(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,
            @RequestParam(value = "start" ) String start,
            @RequestParam(value = "count") String count) {
        if(!stringToInteger(start,count)){
            return renderCodeMsg(httpServletResponse,Code.CODE_REQ_PARAM_ERROR,"invalid params");
        }
        List<Category> list=httpRequestService.getCategoryByPage(Integer.valueOf(start),Integer.valueOf(count));
        if(list == null){
            return renderCodeMsg(httpServletResponse, Code.CODE_SERVER_ERROR,"internal server error");
        }else{
            return renderDate(httpServletResponse,list);
        }
    }

}
