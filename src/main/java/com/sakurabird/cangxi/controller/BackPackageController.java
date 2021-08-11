package com.sakurabird.cangxi.controller;

import com.sakurabird.cangxi.service.BackPackageService;
import com.sakurabird.cangxi.system.data.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "背包接口")
@RestController
@RequestMapping("/backpack")
public class BackPackageController {

    @Autowired
    BackPackageService backPackageService;

    @ApiOperation(value = "查询背包所有物品")
    @GetMapping("/all")
    public ResponseData getAllGoods() {
        return ResponseData.success(backPackageService.getAllGoods());
    }



}
