package com.di.service.httpclient;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;


import java.io.InputStream;

import java.util.List;

/**
 * Created by bentengdi on 2018/5/21.
 */
public class BaseHttpClient {
    private HttpClient client = new DefaultHttpClient() {
    };

    public String get(String baseUrl, String url, List<NameValuePair> nvp, List<Header> headers, List<Cookie> cookies) throws Exception {
        HttpGet httpGet = null;
        HttpEntity httpEntity = null;
        InputStream inputStream = null;
        try{
            if(nvp != null && nvp.size() > 0){
                String queryString = "";
                queryString = EntityUtils.toString(new UrlEncodedFormEntity(nvp,"UTF-8"));
                httpGet = new HttpGet(String.format("%s%s?%s", baseUrl, url, queryString));
            }else {
                httpGet = new HttpGet(String.format("%s%s", baseUrl, url));
            }
            if (headers != null) {
                Header[] headerArrays = headers.toArray(new Header[headers.size()]);
                httpGet.setHeaders(headerArrays);
            }
            HttpResponse response;
            if (cookies != null) {
                BasicCookieStore cookieStore = new BasicCookieStore();
                Cookie[] cookieArrays = cookies.toArray(new Cookie[cookies.size()]);
                cookieStore.addCookies(cookieArrays);
                HttpContext localContext = new BasicHttpContext();
                localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
                response = client.execute(httpGet, localContext);
            } else {
                response = client.execute(httpGet);
            }
            int statusCode = response.getStatusLine().getStatusCode();
            httpEntity = response.getEntity();
            String content = "";
            if (httpEntity != null) {
                content = EntityUtils.toString(httpEntity);
                inputStream = response.getEntity().getContent();
            }
            if (statusCode != 200)
                throw new Exception("请求目标系统发生异常，请求url:" + baseUrl + url
                        + ",响应码：" + statusCode + ",内容:" + content);
            return content;
        } finally {
            if (httpGet != null) {
                httpGet.abort();
            }
            if (httpEntity != null) {
                EntityUtils.consume(httpEntity);
            }
            IOUtils.closeQuietly(inputStream);
        }
    }

    public String post(String baseUrl, String url, List<NameValuePair> nvp, Header... headers)
            throws Exception {
        HttpPost httpPost = null;
        InputStream in = null;
        HttpEntity responseEntity = null;
        try {
            httpPost = new HttpPost(String.format("%s%s", baseUrl, url));
            if (nvp != null) {
                httpPost.setEntity(new UrlEncodedFormEntity(nvp, "UTF-8"));
            }
            if (headers != null) {
                httpPost.setHeaders(headers);
            }

            HttpResponse response = client.execute(httpPost);
            responseEntity = response.getEntity();
            if (responseEntity != null) {
                String content = EntityUtils.toString(responseEntity);
                in = response.getEntity().getContent();
                return content;
            }
        } finally {
            if (httpPost != null) {
                httpPost.abort();
            }
            if (responseEntity != null) {
                EntityUtils.consume(responseEntity);
            }
            IOUtils.closeQuietly(in);
        }
        return null;
    }

}
