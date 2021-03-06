package com.funtl.my.shop.commons.dto;

import java.io.Serializable;

/**
 * 自定义返回类
 */
public class BaseResult implements Serializable {
    public static final int STATUS_SUCCESS = 200;
    public static final int STATUS_FAIL = 500;
    private int status;
    private String message;

    public static BaseResult success(){
        return BaseResult.createBaseResult(STATUS_SUCCESS,"成功");
    }

    public static BaseResult success(String message){
        return BaseResult.createBaseResult(STATUS_SUCCESS,message);
    }

    public static BaseResult fail(){
        return BaseResult.createBaseResult(STATUS_FAIL,"失败");
    }

    public static BaseResult fail(String message){
        return BaseResult.createBaseResult(STATUS_FAIL,message);
    }

    public static BaseResult fail(int status,String message){
        return BaseResult.createBaseResult(status,message);
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BaseResult(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private static BaseResult createBaseResult(int status, String message){
        BaseResult baseResult = new BaseResult(status,message);
        return baseResult;
    }
}
