package com.sakurabird.cangxi.controller;

import com.sakurabird.cangxi.entity.UserEquipment;
import com.sakurabird.cangxi.service.LuckDrawService;
import com.sakurabird.cangxi.service.UserService;
import com.sakurabird.cangxi.system.data.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
*@author vsans@sina.cn
*@date 2021/2/3
*@desc 抽奖接口
**/
@Api(tags = "抽奖接口")
@RestController
@RequestMapping("/luckdraw")
public class LuckDrawController {

    @Autowired
    LuckDrawService luckDrawService;

    @Autowired
    UserService userService;


    @ApiOperation(value = "快乐十连抽")
    @GetMapping("/10happy")
    public ResponseData getHappy() {
        return ResponseData.success(luckDrawService.getHappy());
    }


    @ApiOperation(value = "置换装备")
    @GetMapping("/change/{type}")
    @ApiImplicitParam(name = "type", value = "装备类别",paramType = "path",dataTypeClass=Integer.class)
    public ResponseData changeEqu(@PathVariable("type") Integer type) {
        UserEquipment ue = luckDrawService.changeEqu(type);
        userService.computeData();
        return ResponseData.success(ue);
    }

    @ApiOperation(value = "强化装备")
    @GetMapping("/strengthen/{type}/{magic}")
    @ApiImplicitParam(name = "type", value = "装备类别",paramType = "path",dataTypeClass=Integer.class)
    public ResponseData strengThen(@PathVariable("type") Integer type,@PathVariable("magic") Integer magic) {
        return luckDrawService.strengThen(type,magic);
    }



}
