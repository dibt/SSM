package com.di.service.impl;

import com.di.global.Code;
import com.di.pojo.Category;
import com.di.pojo.response.ResponseCategoryData;
import com.di.service.IHttpRequestService;
import com.di.service.httpclient.HttpClientUtil;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bentengdi on 2018/5/21.
 */
@Service
public class HttpRequestServiceImpl implements IHttpRequestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestServiceImpl.class);
    private static Gson gson= new Gson();
    @Resource(name = "categoryIdApi")
    //@Autowired
    private HttpClientUtil categoryIdApi;
    @Override
    public List<Category> getCategoryByPage(Integer start,Integer count){
        Map<String,String[]> parameterMap = new HashMap<>();
        parameterMap.put("start",new String[]{String.valueOf(start)});
        parameterMap.put("count",new String[]{String.valueOf(count)});
        String responseContent = null;
        try{
            responseContent = categoryIdApi.getResponseObject(parameterMap, HttpMethod.GET.name());
        }catch (Exception e){
            LOGGER.error("http getCategoryByPage resquest error start:{},count:{}", start,count, e);
        }
        if (responseContent != null){
            ResponseCategoryData<Category> resp = (ResponseCategoryData<Category>)gson.fromJson(responseContent, new TypeToken<ResponseCategoryData<Category>>() {
            }.getType());

            if (resp != null && resp.getCode() != null && resp.getCode().equals(Code.CODE_OK) && resp.getData() != null){
                return resp.getData();
            }
        }

        return null;

    }

}
