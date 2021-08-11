package com.sakurabird.cangxi.manager;

import com.sakurabird.cangxi.constvalue.FateConst;
import com.sakurabird.cangxi.entity.Fate;
import com.sakurabird.cangxi.entity.Users;

/**
*@author vsans@sina.cn
*@date 2021/2/4
*@desc 天命管理器
**/
public class FateManager {

    /**
    *@author vsans@sina.cn
    *@date 2021/2/4
    *@params
    *@return
    *@desc 单纯的属性天命
    **/
    public static void computeFate(Fate fate,Users cunrrentUser){

        //跑得快
        if(fate.getId().equals(FateConst.RUN_QUICK)){
            cunrrentUser.setSpeed(cunrrentUser.getSpeed()+1999.00);
        }

        //神偷
        if(fate.getId().equals(FateConst.THIEF_GOD)){
            cunrrentUser.setThief(cunrrentUser.getThief()+5.00);
        }

        //摸钩儿
        if(fate.getId().equals(FateConst.SMALL_THIEF)){
            cunrrentUser.setThief(cunrrentUser.getThief()+3.00);
            cunrrentUser.setSpeed(cunrrentUser.getSpeed()+200.00);
        }

        //残疾人
        if(fate.getId().equals(FateConst.DISABLER)){
            cunrrentUser.setDefense(cunrrentUser.getDefense()*1.20);
            cunrrentUser.setSpeed(cunrrentUser.getSpeed()/2.00);
        }

        //莽夫
        if(fate.getId().equals(FateConst.BULLY)){
            cunrrentUser.setCriticalHit(cunrrentUser.getCriticalHit()+10.00);
            cunrrentUser.setAttackProbability((cunrrentUser.getAttackProbability()-5.00)<0?0:cunrrentUser.getAttackProbability()-5);
        }

        //斗鸡眼
        if(fate.getId().equals(FateConst.COCKEYE)){
            cunrrentUser.setLucky(cunrrentUser.getLucky()+150.00);
            cunrrentUser.setAttackProbability((cunrrentUser.getAttackProbability()-5.00)<0?0:cunrrentUser.getAttackProbability()-5.00);
        }


    }


}
