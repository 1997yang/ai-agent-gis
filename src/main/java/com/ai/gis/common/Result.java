package com.ai.gis.common;

import lombok.Data;

@Data
public class Result<T> {
    private boolean success;
    private Integer code;
    private String message;
    private Object result;

    //构造方法私有
//    private Result(){}

    //成功静态方法
    public static Result ok(String msg) {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS);
        result.setMessage(msg);
        result.setResult(null);
        return result;
    }

    public static Result ok(Object data, String msg) {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS);
        result.setMessage(msg);
        result.setResult(data);
        return result;
    }

    public static Result ok(Object data) {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("成功!");
        result.setResult(data);
        return result;
    }

    //失败静态方法
    public static Result error(String msg) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(ResultCode.ERROR);
        result.setMessage(msg);
        result.setResult(null);
        return result;
    }
}
