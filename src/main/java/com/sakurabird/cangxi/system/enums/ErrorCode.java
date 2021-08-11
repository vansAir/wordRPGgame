package com.sakurabird.cangxi.system.enums;

/**
 *@author vsans@sina.cn
 *@date 2020/7/2
 *@desc 错误码枚举
 **/
public enum ErrorCode {
    //系统异常
    SYS_ERROR(10000,"系统异常"),
    POINT_ERROR(10001,"此处抛出了一个空指针异常,请联系管理员,虽然管理员并不能解决。"),

    //用户错误
    REPEAT_REGISTER(20001,"用户已经存在,无法注册"),
    USER_INFO_BLANK(20002,"这些东西都要填,小老弟"),
    USER_LOGIN_ERROR(20003,"用户名或密码错误"),
    USER_UN_LOGIN(20004,"用户尚未登陆"),
    USER_ERROR_ANSWER(20005,"错误的答题数据"),
    USER_NO_LUCK(20006,"已经没有抽奖次数了"),
    INFO_TOO_SHORT(20007,"这当中的有些属性长度不合规,小老弟"),
    USER_NO_FIGHT(20008,"你今天已经没有打架的体力了"),

    //物品错误
    GOODS_NOT_ENOUGH(30001,"剩余物品不够!"),
    GOODS_STR_FAILED(30002,"强化失败,这太可惜了"),
    GOODS_STR_DESTORY(30003,"哦他妈大嚯,你滴装备碎了一地"),
    GOODS_STR_NOT_DESTORY(30004,"哦他妈大嚯,但是你的装备没碎"),
    GOODS_STR_NOTHING(30005,"哦他妈大嚯,但是大小都保住了"),

    //打架错误
    CANT_FIGHT_OUN(40001,"你不能和自己打锤"),

    DEFAULT(999999999,"");

    private int code;
    private String msg;
    ErrorCode(int code, String msg) {
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
