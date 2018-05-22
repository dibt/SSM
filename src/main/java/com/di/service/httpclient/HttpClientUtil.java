package com.di.service.httpclient;



import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by bentengdi on 2018/5/21.
 */
public class HttpClientUtil extends BaseHttpClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

    private String baseUrl = null;

    private String path = null;

    public String getResponseObject(Map<String,String[]> parameterMap, String httpMethod){
        List<NameValuePair> nvps = null;
        if (parameterMap != null) {
            nvps = getNameValuePair(parameterMap);
        }

        String responseStr = null;

        Long before = System.currentTimeMillis();
        try {
            if (httpMethod.equals(HttpGet.METHOD_NAME)) {
                responseStr = super.get(baseUrl, path, nvps, null, null);
            } else {
                responseStr = super.post(baseUrl, path, nvps, null);
            }
        } catch (Exception ex) {
            LOGGER.error("request error, url:{} responseStr:{} ",baseUrl + path, responseStr, ex);
        } finally {
            Long cost = System.currentTimeMillis() - before;
            LOGGER.info("request url:{} cost:{} responseStr:{}", baseUrl + path, cost, responseStr);
        }

        return responseStr;
    }

    public String getResponseObject(Map<String,String[]> parameterMap, String httpMethod,String path){
        return "";
    }

    private List<NameValuePair> getNameValuePair(Map<String, String[]> paramMaps) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for(Map.Entry<String,String[]> entry: paramMaps.entrySet()){
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()[0]));
        }
        return nameValuePairs;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}