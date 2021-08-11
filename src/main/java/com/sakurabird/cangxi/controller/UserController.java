package com.sakurabird.cangxi.controller;

import cn.hutool.core.util.StrUtil;
import com.sakurabird.cangxi.entity.Users;
import com.sakurabird.cangxi.service.UserService;
import com.sakurabird.cangxi.system.context.HttpContext;
import com.sakurabird.cangxi.system.data.exception.UserException;
import com.sakurabird.cangxi.system.data.response.ResponseData;
import com.sakurabird.cangxi.system.enums.ErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
*@author vsans@sina.cn
*@date 2021/2/1
*@desc 用户接口
**/
@Api(tags = "用户接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;


    @ApiOperation(value = "用户注册接口")
    @PostMapping("/register")
    public ResponseData register(@RequestBody Users users) {
        userService.regist(users);
        return ResponseData.success("注册成功");
    }

    @ApiOperation(value = "用户登陆接口")
    @PostMapping("/login")
    public ResponseData login(@RequestBody Users users) {
        return ResponseData.success(userService.login(users));
    }

    @ApiOperation(value = "用户详情接口")
    @GetMapping("/info")
    public ResponseData getUserInfo() {
        return ResponseData.success(userService.getUserInfo());
    }

    @ApiOperation(value = "获取排行榜")
    @GetMapping("/phb")
    public ResponseData getPhb() {
        return ResponseData.success(userService.getPhb());
    }


    @ApiOperation(value = "添加装备")
    @GetMapping("/ooo")
    public void ooo(@RequestParam("rank") int rank ,@RequestParam("rankstr")String rankstr){userService.ooo(rank,rankstr);
    }

    @ApiOperation(value = "查询所有地点")
    @GetMapping("/areas")
    public ResponseData getArea() {
        return ResponseData.success(userService.getArea());
    }


    @ApiOperation(value = "检查名字重复")
    @GetMapping("/checkname")
    public ResponseData checkName(@RequestParam("userName")String userName,@RequestParam("userPassword")String userPassword,@RequestParam("prefixName") String prefixName,@RequestParam("realName") String realName) {
        if(StrUtil.isBlank(userName)||StrUtil.isBlank(userPassword)||StrUtil.isBlank(prefixName)||StrUtil.isBlank(realName)){
            throw new UserException(ErrorCode.USER_INFO_BLANK);
        }

        //用短细软挑战道德的底线
        if(userName.length()<4||userPassword.length()<6
                ||prefixName.length()<2||realName.length()<2){
            throw new UserException(ErrorCode.INFO_TOO_SHORT);
        }
        userService.checkName(userName,prefixName,realName);
        return ResponseData.success();
    }


}
