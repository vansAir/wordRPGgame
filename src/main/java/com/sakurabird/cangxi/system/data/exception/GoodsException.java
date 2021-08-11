package com.sakurabird.cangxi.system.data.exception;


import com.sakurabird.cangxi.system.enums.ErrorCode;

/**
 *@author vsans@sina.cn
 *@date 2020/7/2
 *@desc 物品异常
 **/
public class GoodsException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    public GoodsException(String message)
    {
        this.message = message;
    }

    public GoodsException(Integer code, String message)
    {
        this.message = message;
        this.code = code;
    }

    public GoodsException(ErrorCode e)
    {
        this.message = e.getMsg();
        this.code = e.getCode();
    }

    public GoodsException(String message, Throwable e)
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
