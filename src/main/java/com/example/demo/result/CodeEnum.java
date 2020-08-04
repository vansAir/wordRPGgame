package com.example.demo.result;

/**
*@author vsans@sina.cn
*@date 2020/7/2
*@desc 返回状态码
**/
public enum CodeEnum {
    //成功
    SUCCESS(200,"成功"),
    //失败
    FAILED(999,"失败");


    private int code;
    private String msg;
    CodeEnum(int code, String msg) {
        this.code=code;
        this.msg=msg;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
