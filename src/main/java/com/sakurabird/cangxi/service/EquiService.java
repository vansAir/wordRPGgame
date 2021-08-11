package com.sakurabird.cangxi.service;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sakurabird.cangxi.dao.*;
import com.sakurabird.cangxi.entity.*;
import com.sakurabird.cangxi.system.util.ArmsRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*@author vsans@sina.cn
*@date 2021/2/1
*@desc 用户服务
**/
@Service
public class EquiService {

    @Autowired
    EquipmentMapper equipmentMapper;

    @Autowired
    UserEquipmentMapper userEquipmentMapper;

    @Autowired
    UserService userService;



    /**
     *@author vsans@sina.cn
     *@date 2021/2/1
     *@params
     *@return
     *@desc 生成默认随机装备
     **/
    public UserEquipment randomEquipment(int type,String rank){
        return randomEquipment(0,type,rank);
    }


    /**
     *@author vsans@sina.cn
     *@date 2021/2/1
     *@params
     *@return
     *@desc 获取装备基础属性
     **/
    public UserEquipment getEquInfo(Integer id){
        QueryWrapper<UserEquipment> ue=new QueryWrapper<>();
        ue.eq("id",id);
        return userEquipmentMapper.selectOne(ue);
    }


    /**
    *@author vsans@sina.cn
    *@date 2021/2/1
    *@params
    *@return
    *@desc 生成随机装备
    **/
    public UserEquipment randomEquipment(Integer userId,int type,String rank){
        QueryWrapper<Equipment> equi=new QueryWrapper<>();
        equi.eq("equ_type",type);
        equi.eq("equ_rank",rank);
        List<Equipment> equis=equipmentMapper.selectList(equi);
        Equipment equ= equis.get(RandomUtil.randomInt(equis.size()));
        UserEquipment uequ=new UserEquipment();
        //修改装备名字
        uequ.setEquipmentName(ArmsRandom.randomEqu(type));
        //属性计算点
        uequ.setAttack(equ.getAttack()*RandomUtil.randomDouble(0.8,1.2));
        uequ.setDefense(equ.getDefense()*RandomUtil.randomDouble(0.8,1.2));
        uequ.setSpeed(equ.getSpeed()*RandomUtil.randomDouble(0.8,1.2));
        uequ.setHealth(equ.getHealth()*RandomUtil.randomDouble(0.8,1.2));
        uequ.setLucky(equ.getLucky()*RandomUtil.randomDouble(0.8,1.2));
        uequ.setKillProbability(equ.getKillProbability()*RandomUtil.randomDouble(0.8,1.2));
        uequ.setCriticalHit(equ.getCriticalHit()*RandomUtil.randomDouble(0.8,1.2));
        uequ.setStrikeBack(equ.getStrikeBack()*RandomUtil.randomDouble(0.8,1.2));
        uequ.setDoubleHit(equ.getDoubleHit()*RandomUtil.randomDouble(0.8,1.2));
        uequ.setThief(equ.getThief()*RandomUtil.randomDouble(0.8,1.2));
        uequ.setAttackProbability(equ.getAttackProbability()*RandomUtil.randomDouble(0.8,1.2));
        uequ.setMissProbability(equ.getMissProbability()*RandomUtil.randomDouble(0.8,1.2));
        uequ.setMoney(equ.getMoney());
        uequ.setEquRank(equ.getEquRank());
        uequ.setUserId(userId);
        uequ.setLevel(0);
        uequ.setEquType(equ.getEquType());
        uequ.setEquDescribe("");
        //删除原装备
        QueryWrapper<UserEquipment> uew=new QueryWrapper<>();
        uew.eq("user_id",userId);
        uew.eq("equ_type",type);
        userEquipmentMapper.delete(uew);
        //增加新装备
        userEquipmentMapper.insert(uequ);
        //修改人物装备
        Users user=new Users();
        user.setId(userId);
        int et=uequ.getEquType();
        if(et==1){
            user.setHead(uequ.getId().toString());
        }
        if(et==2){
            user.setBody(uequ.getId().toString());
        }
        if(et==3){
            user.setShoes(uequ.getId().toString());
        }
        if(et==4){
            user.setArms(uequ.getId().toString());
        }if(et==5){
            user.setOrnaments(uequ.getId().toString());
        }
        userService.updateById(user);
//        userService.computeData();
        return uequ;
    }

    public void updateEquiUserId(Integer equiId,Integer userId){
        UserEquipment ue=new UserEquipment();
        ue.setId(equiId);
        ue.setUserId(userId);
        userEquipmentMapper.updateById(ue);
    }
}
