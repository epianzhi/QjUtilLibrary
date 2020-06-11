package com.qj.ulibrary.retrofit.model;

/**
 * Created by helin on 2016/10/10 11:44.
 * 实体的基类
 */
public class HttpResult<T> {



    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
