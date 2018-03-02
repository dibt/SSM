package com.di.controller;

import com.di.global.ErrorCode;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bentengdi on 2018/3/1.
 */
public class BaseController {
    protected Map<String,Object> renderDate(HttpServletResponse response,Object result){
        response.setContentType("application/json;charset=UTF-8");
        Map<String,Object> map=new HashMap<>();
        map.put(ErrorCode.CODE_KEY,ErrorCode.CODE_OK);
        map.put(ErrorCode.MSG_KEY,ErrorCode.SUCCESS_KEY);
        map.put(ErrorCode.TIMETAMP_KEY,System.currentTimeMillis());
        if(result != null){
            map.put(ErrorCode.DATE_KEY,result);
        }
        return map;
    }

    protected Map<String,Object> renderErrorDate(HttpServletResponse response,Integer errorcode,String message){
        response.setContentType("application/json;charset=UTF-8");
        Map<String,Object> map=new HashMap<>();
        map.put(ErrorCode.CODE_KEY,errorcode);
        map.put(ErrorCode.MSG_KEY,message);
        map.put(ErrorCode.TIMETAMP_KEY,System.currentTimeMillis());
        return map;
    }




    protected boolean stringToInteger(String... stringArray){
        //String to Integer
        for(String string:stringArray){
            try{
                Integer.valueOf(string);
            }catch (Exception e){
                return false;
            }
        }
        return  true;
    }
}
