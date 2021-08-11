package com.sakurabird.cangxi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sakurabird.cangxi.dao.BackPackageMapper;
import com.sakurabird.cangxi.entity.BackPackage;
import com.sakurabird.cangxi.entity.Goods;
import com.sakurabird.cangxi.system.context.HttpContext;
import com.sakurabird.cangxi.system.data.exception.GoodsException;
import com.sakurabird.cangxi.system.enums.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*@author vsans@sina.cn
*@date 2021/2/3
*@desc 背包物品服务
**/
@Service
public class BackPackageService {

    @Autowired
    BackPackageMapper backPackageMapper;

    /**
    *@author vsans@sina.cn
    *@date 2021/2/3
    *@params
    *@return
    *@desc 获取用户背包内所有的物品
    **/
    public List<BackPackage> getAllGoods(){
        Integer id=HttpContext.getId();
        QueryWrapper<BackPackage> bp=new QueryWrapper<>();
        bp.eq("user_id",id);
        return backPackageMapper.selectList(bp);
    }

    /**
     *@author vsans@sina.cn
     *@date 2021/2/3
     *@params
     *@return
     *@desc 背包物品加减操作
     **/
    public void manageGoods(Goods goods, Integer number){
        Integer id=HttpContext.getId();
        manageGoods(goods,number,id);
    }
    /**
     *@author vsans@sina.cn
     *@date 2021/2/3
     *@params
     *@return
     *@desc 背包物品加减操作带id
     **/
    public void manageGoods(Goods goods, Integer number,Integer id){

        QueryWrapper<BackPackage> bpqw=new QueryWrapper<>();
        bpqw.eq("user_id",id);
        bpqw.eq("goods_id",goods.getId());
        BackPackage bp=backPackageMapper.selectOne(bpqw);
        if(bp==null&&number<0){
            throw new GoodsException(ErrorCode.GOODS_NOT_ENOUGH);
        }
        if(bp==null&&number>0){
            bp=new BackPackage();
            bp.setGoodsId(goods.getId());
            bp.setGoodsName(goods.getGoodsName());
            bp.setGoodsDesc(goods.getGoodsDesc());
            bp.setGoodsRank(goods.getGoodsRank());
            bp.setNums(number);
            bp.setUserId(id);
            bp.setCanUse(goods.isCanUse());
            bp.setCanThief(goods.isCanThief());
            backPackageMapper.insert(bp);
            return;
        }

        int become=bp.getNums()+number;
        if(become<0){
            throw new GoodsException(ErrorCode.GOODS_NOT_ENOUGH);
        }

        if(become==0){
            backPackageMapper.deleteById(bp.getId());
        }

        bp.setGoodsId(goods.getId());
        bp.setGoodsName(goods.getGoodsName());
        bp.setGoodsDesc(goods.getGoodsDesc());
        bp.setGoodsRank(goods.getGoodsRank());
        bp.setNums(become);
        bp.setUserId(id);
        bp.setCanUse(goods.isCanUse());
        bp.setCanThief(goods.isCanThief());
        backPackageMapper.updateById(bp);

    }
}
