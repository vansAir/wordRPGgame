package com.sakurabird.cangxi.system.data.exception;


import com.sakurabird.cangxi.system.enums.ErrorCode;

/**
 *@author vsans@sina.cn
 *@date 2020/7/2
 *@desc 用户
 **/
public class UserException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    public UserException(String message)
    {
        this.message = message;
    }

    public UserException(Integer code,String message)
    {
        this.message = message;
        this.code = code;
    }

    public UserException(ErrorCode e)
    {
        this.message = e.getMsg();
        this.code = e.getCode();
    }

    public UserException(String message, Throwable e)
    {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public Integer getCode()
    {
        return code;
    }


}
