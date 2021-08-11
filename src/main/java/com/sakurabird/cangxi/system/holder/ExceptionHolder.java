package com.sakurabird.cangxi.system.holder;

import com.sakurabird.cangxi.system.data.exception.GoodsException;
import com.sakurabird.cangxi.system.data.exception.UserException;
import com.sakurabird.cangxi.system.data.response.ResponseData;
import com.sakurabird.cangxi.system.enums.ErrorCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHolder {

    /**
     * 用户错误
     *
     * @author fengshuonan
     * @Date 2020/2/5 11:50 下午
     */
    @ExceptionHandler(UserException.class)
    @ResponseBody
    public ResponseData userError(UserException e) {
        return ResponseData.error(e.getCode(), e.getMessage());
    }

    /**
     * 物品错误
     *
     * @author fengshuonan
     * @Date 2020/2/5 11:50 下午
     */
    @ExceptionHandler(GoodsException.class)
    @ResponseBody
    public ResponseData goodsError(GoodsException e) {
        return ResponseData.error(e.getCode(), e.getMessage());
    }

    /**
     * 空指针异常
     *
     * @author fengshuonan
     * @Date 2020/2/5 11:50 下午
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResponseData pointError(NullPointerException e) {
        return ResponseData.error(ErrorCode.POINT_ERROR.getCode(), ErrorCode.POINT_ERROR.getMsg());
    }


}
