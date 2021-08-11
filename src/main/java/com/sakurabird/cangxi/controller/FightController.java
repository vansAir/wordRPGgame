package com.sakurabird.cangxi.controller;

import com.sakurabird.cangxi.service.FightService;
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

import java.util.HashMap;
import java.util.Map;

@Api(tags = "打架接口")
@RestController
@RequestMapping("/fight")
public class FightController {

    @Autowired
    FightService fightService;

    @ApiOperation(value = "打架详情接口")
    @GetMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "打架对象id",paramType = "path",dataTypeClass=Integer.class)
    public ResponseData fightWith(@PathVariable("id") Integer id) {
        if(id.equals(HttpContext.getId())){
            throw new UserException(ErrorCode.CANT_FIGHT_OUN);
        }
        return ResponseData.success(fightService.fightWith(id));
    }

}
