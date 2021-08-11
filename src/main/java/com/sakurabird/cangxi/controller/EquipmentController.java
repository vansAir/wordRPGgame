package com.sakurabird.cangxi.controller;

import com.sakurabird.cangxi.service.EquiService;
import com.sakurabird.cangxi.system.data.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "装备接口")
@RestController
@RequestMapping("/equ")
public class EquipmentController {

    @Autowired
    EquiService equiService;

    @ApiOperation(value = "装备详情详情接口")
    @GetMapping("/{id}")
    @ApiImplicitParam(name = "id", value = "装备id",paramType = "path",dataTypeClass=Integer.class)
    public ResponseData getEquInfo(@PathVariable("id") Integer id) {
        return ResponseData.success(equiService.getEquInfo(id));
    }

}
