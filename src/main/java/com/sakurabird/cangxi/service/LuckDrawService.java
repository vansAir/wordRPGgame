package com.sakurabird.cangxi.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sakurabird.cangxi.constvalue.GlobalConst;
import com.sakurabird.cangxi.constvalue.GoodsConst;
import com.sakurabird.cangxi.dao.*;
import com.sakurabird.cangxi.entity.*;
import com.sakurabird.cangxi.system.context.HttpContext;
import com.sakurabird.cangxi.system.data.exception.UserException;
import com.sakurabird.cangxi.system.data.response.ResponseData;
import com.sakurabird.cangxi.system.enums.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
*@author vsans@sina.cn
*@date 2021/2/3
*@desc 抽奖服务
**/
@Service
public class LuckDrawService {

    @Autowired
    TimesControlMapper timesControlMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    BackPackageMapper backPackageMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserEquipmentMapper userEquipmentMapper;

    @Autowired
    BackPackageService backPackageService;

    @Autowired
    EquiService equiService;

    @Autowired
    UserService userService;


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
    *@author vsans@sina.cn
    *@date 2021/2/3
    *@params
    *@return
    *@desc 十连抽方法
    **/
    public synchronized Map<String,Integer> getHappy(){
        //数据处理和验证
        Integer id = HttpContext.getId();
        QueryWrapper<TimesControl> ldqw=new QueryWrapper<>();
        ldqw.eq("user_id",id);
        ldqw.eq("use_type","luckDraw");
        TimesControl ld=timesControlMapper.selectOne(ldqw);
        if(ld==null||ld.getUseTimes()<1){
            throw new UserException(ErrorCode.USER_NO_LUCK);
        }
        ld.setUseTimes(ld.getUseTimes()-1);
        timesControlMapper.updateById(ld);
        //开奖
        Map<String,Integer> map=new HashMap<>();
        List<Goods> gds=goodsMapper.selectList(null);
        for (int i = 0; i < 10; i++) {
            String rank=randomRank();
            List<Goods> cgds=gds.stream().filter(e->rank.equals(e.getGoodsRank())).collect(Collectors.toList());
            Goods gd=cgds.get(RandomUtil.randomInt(cgds.size()));
            map.merge(gd.getGoodsName(),1,Integer::sum);
            backPackageService.manageGoods(gd,1);
        }

        return map;
    }

    /**
    *@author vsans@sina.cn
    *@date 2021/2/4
    *@params
    *@return
    *@desc 置换装备的接口
    **/
    public synchronized UserEquipment changeEqu(Integer type){
        Integer id = HttpContext.getId();
        //数量减一
        backPackageService.manageGoods(goodsMapper.selectById(GoodsConst.CHANGE_EQU_PAPER),-1);
        //抽取评级
        String rank=randomRank();
        //抽取装备
        UserEquipment ue = equiService.randomEquipment(id,type,rank);

        //修改User装备
        Users users=new Users();
        users.setId(id);
        if(type==1){
            users.setHead(ue.getId().toString());
            userMapper.updateById(users);
        }

        if(type==2){
            users.setBody(ue.getId().toString());
            userMapper.updateById(users);
        }
        if(type==3){
            users.setShoes(ue.getId().toString());
            userMapper.updateById(users);
        }

        if(type==4){
            users.setArms(ue.getId().toString());
            userMapper.updateById(users);
        }

        if(type==5){
            users.setOrnaments(ue.getId().toString());
            userMapper.updateById(users);
        }
        ue.setUserId(0);
        userService.computeData();
        return  ue;

    }

    /**
    *@author vsans@sina.cn
    *@date 2021/2/4
    *@params
    *@return
    *@desc 装备强化接口
    **/
    public synchronized ResponseData strengThen(Integer type,Integer magic){
        Integer id = HttpContext.getId();
        //处理强化类型
        Map<String,Boolean> magicMap=checkMagic(magic);

        //查询出强化的装备
        QueryWrapper<UserEquipment> ueqw=new QueryWrapper<>();
        ueqw.eq("user_id",id);
        ueqw.eq("equ_type",type);
        UserEquipment ue = userEquipmentMapper.selectOne(ueqw);
        //计算所需的石头
        int neednum=computeStone(ue);
        backPackageService.manageGoods(goodsMapper.selectById(GoodsConst.STRENGTHEN_STONE),-neednum);
        //处理+10卷
        if(magicMap.get("10")){
            ue.setLevel(10);
            userEquipmentMapper.updateById(ue);
            return ResponseData.success(ue);
        }
        //处理+12卷
        if(magicMap.get("12")){
            ue.setLevel(12);
            userEquipmentMapper.updateById(ue);
            return ResponseData.success(ue);
        }

        //开始强化
        int curand=RandomUtil.randomInt(101);
        int strrank=1;

        //处理玉碎强化
        if(magicMap.get("玉碎")){
            if(curand>97){
                ue.setLevel(ue.getLevel()*2);
                userEquipmentMapper.updateById(ue);
                return ResponseData.success(ue);
            }else{
                ue=equiService.randomEquipment(id,ue.getEquType(),"N");
                return  ResponseData.error(ErrorCode.GOODS_STR_DESTORY.getCode(),ErrorCode.GOODS_STR_DESTORY.getMsg(),ue);
            }
        }

        //处理随机强化
        if(magicMap.get("随机")){
            if(ue.getLevel()<10){
            curand=RandomUtil.randomInt(ue.getLevel()*10+6);
            }
            strrank=RandomUtil.randomInt(1,6);
        }
        if(ue.getLevel()<10){
            //强化成功率逐级递减
            if(curand>ue.getLevel()*10){
                //双倍成功卡
                if(magicMap.get("双倍")){
                    strrank++;
                }
                ue.setLevel(ue.getLevel()+strrank);
                userEquipmentMapper.updateById(ue);
            }else {
                int sub=RandomUtil.randomInt(ue.getLevel()==0?1:ue.getLevel());
                if(magicMap.get("保级")){
                    sub=0;
                }
                ue.setLevel(ue.getLevel()-sub);
                userEquipmentMapper.updateById(ue);
                return  ResponseData.error(ErrorCode.GOODS_STR_FAILED.getCode(),ErrorCode.GOODS_STR_FAILED.getMsg(),ue);
            }
        }else{
            if(curand>95){
                ue.setLevel(ue.getLevel()+1);
                userEquipmentMapper.updateById(ue);
            }else {
                if(magicMap.get("双保")){
                    return  ResponseData.error(ErrorCode.GOODS_STR_NOTHING.getCode(),ErrorCode.GOODS_STR_NOTHING.getMsg(),ue);
                }
                else if (magicMap.get("保碎")){
                    ue.setLevel(0);
                    userEquipmentMapper.updateById(ue);
                    return  ResponseData.error(ErrorCode.GOODS_STR_NOT_DESTORY.getCode(),ErrorCode.GOODS_STR_NOT_DESTORY.getMsg(),ue);
                }else {
                    ue=equiService.randomEquipment(id,ue.getEquType(),"N");
                    return  ResponseData.error(ErrorCode.GOODS_STR_DESTORY.getCode(),ErrorCode.GOODS_STR_DESTORY.getMsg(),ue);
                }
            }

        }
        return ResponseData.success(ue);
    }

    /**
    *@author vsans@sina.cn
    *@date 2021/7/29
    *@params
    *@return
    *@desc 验证抽奖是否具有物品
    **/
    Map<String,Boolean> checkMagic(Integer magic){
        //把状态塞进去
        Map<String,Boolean> magicMap=new HashMap<>();
        magicMap.put("保级",false);
        magicMap.put("保碎",false);
        magicMap.put("双倍",false);
        magicMap.put("玉碎",false);
        magicMap.put("随机",false);
        magicMap.put("10",false);
        magicMap.put("12",false);
        magicMap.put("双保",false);

        switch (magic){
            case GoodsConst.SAVE_EQU_PAPER:
                backPackageService.manageGoods(goodsMapper.selectById(GoodsConst.SAVE_EQU_PAPER),-1);
                magicMap.put("保级",true);
                break;
            case  GoodsConst.GET_EQU_PAPER:
                backPackageService.manageGoods(goodsMapper.selectById(GoodsConst.GET_EQU_PAPER),-1);
                magicMap.put("保碎",true);
                break;
            case  GoodsConst.DOUBLE_EQU_PAPER:
                backPackageService.manageGoods(goodsMapper.selectById(GoodsConst.DOUBLE_EQU_PAPER),-1);
                magicMap.put("双倍",true);
                break;
            case  GoodsConst.ONE_LIVE_EQU_PAPER:
                backPackageService.manageGoods(goodsMapper.selectById(GoodsConst.ONE_LIVE_EQU_PAPER),-1);
                magicMap.put("玉碎",true);
                break;
            case  GoodsConst.RAN_CHANGE_EQU_PAPER:
                backPackageService.manageGoods(goodsMapper.selectById(GoodsConst.RAN_CHANGE_EQU_PAPER),-1);
                magicMap.put("随机",true);
                break;
            case  GoodsConst.TEN_EQU_PAPER:
                backPackageService.manageGoods(goodsMapper.selectById(GoodsConst.TEN_EQU_PAPER),-1);
                magicMap.put("10",true);
                break;
            case  GoodsConst.TWELVE_EQU_PAPER:
                backPackageService.manageGoods(goodsMapper.selectById(GoodsConst.TWELVE_EQU_PAPER),-1);
                magicMap.put("12",true);
                break;
            case  GoodsConst.SAVE_GET_EQU_PAPER:
                backPackageService.manageGoods(goodsMapper.selectById(GoodsConst.SAVE_GET_EQU_PAPER),-1);
                magicMap.put("双保",true);
                break;

            default:break;
        }

        return magicMap;
    }

    /**
    *@author vsans@sina.cn
    *@date 2021/2/3
    *@params
    *@return
    *@desc 评级抽取方法
    **/
    public String randomRank(){
        Integer id=HttpContext.getId();
        Users users= JSONUtil.toBean(redisTemplate.boundValueOps(GlobalConst.USDATA+id).get().toString(),Users.class);

        //基础数据
        int N=700;
        int R=600;
        int B=500;
        int A=400;
        int S=200;
        int SS=80;
        int SSS=4;
        int Z=3;
        int EX=1;
        //幸运值加持方案一
        N=(int)(N-(users.getLucky()/3));
        int total=N+R+B+A+S+SS+SSS+Z+EX;
        int r1=N;
        int r2=r1+R;
        int r3=r2+B;
        int r4=r3+A;
        int r5=r4+S;
        int r6=r5+SS;
        int r7=r6+SSS;
        int r8=r7+Z;
        int r9=r8+EX;
        //幸运值加持
        int rd=RandomUtil.randomInt(1,total+1);
//        if(rd<=(total-users.getLucky())){
//            rd+=users.getLucky();
//        }

        if(rd<=r1){
            return "N";
        }

        if(rd<=r2){
            return "R";
        }

        if(rd<=r3){
            return "B";
        }

        if(rd<=r4){
            return "A";
        }

        if(rd<=r5){
            return "S";
        }

        if(rd<=r6){
            return "SS";
        }

        if(rd<=r7){
            return "SSS";
        }

        if(rd<=r8){
            return "Z";
        }

        if(rd<=r9){
            return "EX";
        }

        return "";
    }

    /**
    *@author vsans@sina.cn
    *@date 2021/2/4
    *@params
    *@return
    *@desc 计算强化所需的强化石
    **/
    private int computeStone(UserEquipment userEquipment){
        int basenum=0;
        if(userEquipment.getEquRank().equals("N")){
            basenum=1;
        }

        if(userEquipment.getEquRank().equals("B")){
            basenum=2;
        }


        if(userEquipment.getEquRank().equals("A")){
            basenum=3;
        }


        if(userEquipment.getEquRank().equals("S")){
            basenum=4;
        }


        if(userEquipment.getEquRank().equals("SS")){
            basenum=5;
        }


        if(userEquipment.getEquRank().equals("SSS")){
            basenum=6;
        }


        if(userEquipment.getEquRank().equals("Z")){
            basenum=7;
        }


        if(userEquipment.getEquRank().equals("EX")){
            basenum=8;
        }

        return (userEquipment.getLevel()+1)*basenum*2;
    }

}
