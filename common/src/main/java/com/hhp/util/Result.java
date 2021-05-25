package com.hhp.util;

import lombok.Data;
import lombok.Setter;

@Data
public class Result<T> {
    //响应码
    private Integer code;
    private String message;
    //响应数据
    private T data;

    public static <T> Result<T> ok(T data) {
        Result<T> result = ok();
        result.setData(data);
        return result;
    }

    public static Result ok() {
        Result result = new Result();
        result.setCode(ResponseEnum.SUCCESS.getCode());
        result.setMessage(ResponseEnum.SUCCESS.getMessage());
        return result;
    }
}
