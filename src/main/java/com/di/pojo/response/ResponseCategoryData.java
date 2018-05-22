package com.di.pojo.response;

import java.util.List;

/**
 * Created by bentengdi on 2018/5/22.
 */
public class ResponseCategoryData<T> {

    private Long ts;

    private Integer code;

    private String message;

    private List<T> data;

    public Long getTimeCost() {
        return ts;
    }

    public void setTimeCost(Long timeCost) {
        this.ts = timeCost;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
