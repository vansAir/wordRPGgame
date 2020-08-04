package com.example.demo.result;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String msg;

    private T data;

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public static com.example.demo.result.Result success(String msg, Object data){
        return  new com.example.demo.result.Result(CodeEnum.SUCCESS.getCode(),msg,data);
    }

    public static com.example.demo.result.Result success(String msg){
        return  new com.example.demo.result.Result(CodeEnum.SUCCESS.getCode(),msg);
    }

    public static com.example.demo.result.Result success(Object data){
        return  new com.example.demo.result.Result(CodeEnum.SUCCESS.getCode(),data);
    }


    public static com.example.demo.result.Result failed(String msg, Object data){
        return  new com.example.demo.result.Result(CodeEnum.FAILED.getCode(),msg,data);
    }

    public static com.example.demo.result.Result failed(String msg){return  new com.example.demo.result.Result(CodeEnum.FAILED.getCode(),msg);}

    public static com.example.demo.result.Result failed(Object data){return  new com.example.demo.result.Result(CodeEnum.FAILED.getCode(),data);}

}
