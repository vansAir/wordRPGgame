package com.sakurabird.cangxi.constvalue;

/**
 *@author vsans@sina.cn
 *@date 2020/7/2
 *@desc 错误码枚举
 **/
public enum FateEnum {
    //系统异常
    SYS_ERROR(10000,"系统异常"),
    POINT_ERROR(10001,"此处抛出了一个空指针异常,请联系管理员,虽然管理员并不能解决。"),

    //用户错误
    REPEAT_REGISTER(20001,"用户已经存在,无法注册"),
    USER_INFO_BLANK(20002,"用户信息不能为空"),
    USER_LOGIN_ERROR(20003,"用户名或密码错误"),
    USER_UN_LOGIN(20004,"用户尚未登陆"),
    USER_ERROR_ANSWER(20005,"错误的答题数据"),
    USER_NO_LUCK(20006,"已经没有抽奖次数了"),

    //物品错误
    GOODS_NOT_ENOUGH(30001,"剩余物品不够"),

    DEFAULT(999999999,"");

    private int code;
    private String msg;
    FateEnum(int code, String msg) {
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
