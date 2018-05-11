package com.di.controller;

import com.di.global.Code;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bentengdi on 2018/3/1.
 */
public class BaseController {
    protected Map<String,Object> renderDate(HttpServletResponse response,Object result){
        response.setContentType("application/json;charset=UTF-8");
        Map<String,Object> map=new HashMap<String,Object>();
        map.put(Code.CODE_KEY,Code.CODE_OK);
        map.put(Code.MSG_KEY,Code.SUCCESS_KEY);
        map.put(Code.TIMETAMP_KEY,System.currentTimeMillis());
        if(result != null){
            map.put(Code.DATE_KEY,result);
        }
        return map;
    }

    protected Map<String,Object> renderCodeMsg(HttpServletResponse response,Integer errorcode,String message){
        response.setContentType("application/json;charset=UTF-8");
        Map<String,Object> map=new HashMap<String,Object>();
        map.put(Code.CODE_KEY,errorcode);
        map.put(Code.MSG_KEY,message);
        map.put(Code.TIMETAMP_KEY,System.currentTimeMillis());
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

    protected  boolean stringToDouble(String... stringArray){
        //String to Double
        for(String string:stringArray){
            try{
                Double.parseDouble(string);
            }catch (Exception e){
                return false;
            }
        }
        return true;
    }
}
