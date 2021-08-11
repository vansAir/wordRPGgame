package com.sakurabird.cangxi.controller;


import com.sakurabird.cangxi.service.GamblingService;
import com.sakurabird.cangxi.system.context.HttpContext;
import com.sakurabird.cangxi.system.data.exception.UserException;
import com.sakurabird.cangxi.system.data.response.ResponseData;
import com.sakurabird.cangxi.system.enums.ErrorCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "赌博接口")
@RestController
@RequestMapping("/gambling")
public class GamblingController {

    @Autowired
    GamblingService gamblingService;

    @ApiOperation(value = "赌博详情接口")
    @GetMapping("/{goodsId}/{magnification}")
    @ApiImplicitParam(name = "goodsId", value = "打架对象id",paramType = "path",dataTypeClass=Integer.class)
    public ResponseData fightWith(@PathVariable("goodsId") Integer goodsId,@PathVariable("magnification") int magnification) {
        return ResponseData.success(gamblingService.gamblingAction(goodsId,magnification));
    }
}
