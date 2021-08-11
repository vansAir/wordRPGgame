package com.sakurabird.cangxi.service;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sakurabird.cangxi.dao.GoodsMapper;
import com.sakurabird.cangxi.dao.SkillMapper;
import com.sakurabird.cangxi.entity.Goods;
import com.sakurabird.cangxi.entity.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*@author vsans@sina.cn
*@date 2021/2/2
*@desc 用户服务
**/
@Service
public class GamblingService {

    @Autowired
    SkillMapper skillMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    BackPackageService backPackageService;

    public Boolean gamblingAction(Integer goodsId,int magnification){
        Goods goods=goodsMapper.selectById(goodsId);
        backPackageService.manageGoods(goods,-1);
        int add=1;
        int ran=0;
        switch (magnification){
            case 1:
                ran=RandomUtil.randomInt(100);
                if(ran<40){
                    add+=1;
                }
                break;
            case 2:
                ran=RandomUtil.randomInt(100);
                if(ran<30){
                    add+=2;
                }
                break;
            case 3:
            ran=RandomUtil.randomInt(100);
            if(ran<20){
                add+=3;
            }
                break;

            case 4:
            ran=RandomUtil.randomInt(100);
            if(ran<10){
                add+=4;
            }
                break;
            case 5:
            ran=RandomUtil.randomInt(100);
            if(ran<5){
                add+=5;
            }
                break;
            default:
                break;
        }

        if(add>1){
            backPackageService.manageGoods(goods,add);
            return true;
        }else {
            return false;
        }
    }

}
