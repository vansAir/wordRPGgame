package com.sakurabird.cangxi.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sakurabird.cangxi.constvalue.GlobalConst;
import com.sakurabird.cangxi.dao.BackPackageMapper;
import com.sakurabird.cangxi.dao.GoodsMapper;
import com.sakurabird.cangxi.dao.SkillMapper;
import com.sakurabird.cangxi.dao.TimesControlMapper;
import com.sakurabird.cangxi.entity.BackPackage;
import com.sakurabird.cangxi.entity.Goods;
import com.sakurabird.cangxi.entity.TimesControl;
import com.sakurabird.cangxi.entity.Users;
import com.sakurabird.cangxi.system.context.HttpContext;
import com.sakurabird.cangxi.system.data.exception.UserException;
import com.sakurabird.cangxi.system.enums.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
*@author vsans@sina.cn
*@date 2021/2/2
*@desc 打架服务
**/
@Service
public class FightService {

    @Autowired
    SkillMapper skillMapper;

    @Autowired
    TimesControlMapper timesControlMapper;

    @Autowired
    BackPackageMapper backPackageMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    BackPackageService backPackageService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    LuckDrawService luckDrawService;


    public Map<String,Object> fightWith(Integer id){
        //获取打架双方
        int  myid=HttpContext.getId();
        Users iam= JSONUtil.toBean(redisTemplate.boundValueOps(GlobalConst.USDATA+myid).get().toString(),Users.class);
        Users ur= JSONUtil.toBean(redisTemplate.boundValueOps(GlobalConst.USDATA+id).get().toString(),Users.class);
        //查询是否还有打架次数
        QueryWrapper<TimesControl> ftq=new QueryWrapper<>();
        ftq.eq("user_id",myid);
        ftq.eq("use_type","fight");
        TimesControl tc=timesControlMapper.selectOne(ftq);
        if(tc.getUseTimes()<1){
            throw new UserException(ErrorCode.USER_NO_FIGHT);
        }else{
            tc.setUseTimes(tc.getUseTimes()-1);
            timesControlMapper.updateById(tc);
        }

        //创建结果
        Map<String,Object> res=new HashMap<>();

        List<Users> ss=new ArrayList<>();
        ss.add(iam);
        ss.add(ur);
        //定义属性
        double ma;
        double m;
        double k;
        double c;
        double s;
        double t;
        double dou;
        int currentRound=0;
        int totalRound=1;

        if(iam.getSpeed()<ur.getSpeed()){
            currentRound=1;
        }
        //创建战场
        StringBuffer situation=new StringBuffer();
        iam.setThiefThing(new StringBuffer());
        ur.setThiefThing(new StringBuffer());
        situation.append(situationStr(iam.getRealName(),ur.getRealName()));
        while (iam.getHealth()>0&&ur.getHealth()>0){
            Users a=ss.get(currentRound);
            Users d=ss.get(currentRound^1);
            String an=a.getRealName();
            String dn=d.getRealName();

            situation.append(String.format("第【%d】回合 <br> ",totalRound));
            //计算必中
            ma=RandomUtil.randomDouble(100.0);
            //计算闪避
            m= RandomUtil.randomDouble(100.00);

            //必中则跳过闪避 否则计算闪避
            if(ma<a.getAttackProbability()){
                situation.append(atcStr(an,dn));
            }else if(m<d.getMissProbability()){
                situation.append(missStr(an,dn));
                totalRound++;
                currentRound=currentRound^1;
                situation.append(currHealthStr(an,a.getHealth(),dn,d.getHealth()));
                continue;
            }

            //计算偷窃概率
            t=RandomUtil.randomDouble(100.0);
            if(t<a.getThief()){
                String thiefRank = luckDrawService.randomRank();
                QueryWrapper<BackPackage> thq=new QueryWrapper<>();
                thq.eq("goods_rank",thiefRank);
                thq.eq("user_id",d.getId());
                List<BackPackage> bps=backPackageMapper.selectList(thq);
                if(bps.size()>0){
                    BackPackage bp=bps.get(RandomUtil.randomInt(bps.size()));
                    Goods goods=goodsMapper.selectById(bp.getGoodsId());
                    backPackageService.manageGoods(goods,-1,d.getId());
                    backPackageService.manageGoods(goods,1,a.getId());
                    a.getThiefThing().append(goods.getGoodsName()+",");
                    situation.append(thiStr(an,dn,goods.getGoodsName()));
                }else {
                    situation.append(thiFailStr(an,dn));
                }
            }

            //计算即死 成功扣除当前10%的血量
            k= RandomUtil.randomDouble(100.0);
            if(k<a.getAttackProbability()){
                double dmg=d.getHealth()*0.1;
                d.setHealth(d.getHealth()-dmg);
                situation.append(killStr(an,dn,dmg));
            }

            //本次共计伤害浮动
            double currentDmg=a.getAttack()*RandomUtil.randomDouble(0.1,1.2);
            //计算暴击
            c=RandomUtil.randomDouble(100.0);
            if(c<a.getCriticalHit()){
                currentDmg*=2;
                situation.append(criStr(an,dn));
            }

            //计算反击
            s=RandomUtil.randomDouble(100.0);
            if(s<d.getStrikeBack()){
                double dmg=d.getAttack()*RandomUtil.randomDouble(0.1,0.3);
                a.setHealth(a.getHealth()-dmg);
                situation.append(striStr(an,dn,dmg));
            }

            //计算硬伤害
            currentDmg=currentDmg*(1-(d.getDefense()/(d.getDefense()+1300)));
            d.setHealth(d.getHealth()-currentDmg);
            situation.append(dmgStr(an,dn,currentDmg));

            //计算连击
            dou=RandomUtil.randomDouble(100.0);
            if(dou<a.getDoubleHit()){
                situation.append(douStr(an,dn));
                situation.append(currHealthStr(an,a.getHealth(),dn,d.getHealth()));
                totalRound++;
                continue;
            }
            totalRound++;
            currentRound=currentRound^1;
            situation.append(currHealthStr(an,a.getHealth(),dn,d.getHealth()));
        }

        if(iam.getHealth()<=0){
            situation.append(failStr(iam.getRealName(),ur.getRealName()));
        }else {
            situation.append(winStr(iam.getRealName(),ur.getRealName()));
        }
        res.put("situation",situation);
        res.put("ithief",iam.getThiefThing());
        res.put("uthief",ur.getThiefThing());
        return res;
    }

    String situationStr(String a,String d){
        String[] sit={
                "%s走在该上，看到%s说：那你局我鸭儿不？于是两人扭打了起来。 <br> ",
        };
        return String.format(sit[RandomUtil.randomInt(sit.length)],a,d);
    }

    String atcStr(String a,String d){
        String[] sit={
                "%s想闪，但是并没有闪过，%s抡着武器就上来了。 <br> ",
        };
        return String.format(sit[RandomUtil.randomInt(sit.length)],d,a);
    }

    String thiStr(String a,String d,String th){
        String[] sit={
                "%s将手伸进了%s的裤裆，突然发现什么东西硬邦邦，掏出来一看，居然是<span style=\"color:#cc66ff\">【%s】</span>。 <br> ",
        };
        return String.format(sit[RandomUtil.randomInt(sit.length)],a,d,th);
    }

    String thiFailStr(String a,String d){
        String[] sit={
                "%s将手伸进了%s的裤裆，确什么都没摸到。 <br> ",
        };
        return String.format(sit[RandomUtil.randomInt(sit.length)],a,d);
    }

    String missStr(String a,String d){
        String[] sit={
                "%s攒劲乳了一锭子，却被%s灵巧的躲开了，他还说：哈麻皮，打不打得上嘛。 <br> ",
        };
        return String.format(sit[RandomUtil.randomInt(sit.length)],a,d);
    }

    String killStr(String a,String d,double dmg){
        String[] sit={
                "%s不讲武德，来，骗，来，偷袭%s六十岁老同志，当即对他造成了%.2f即死伤害。 <br> ",
        };
        return String.format(sit[RandomUtil.randomInt(sit.length)],a,d,dmg);
    }

    String criStr(String a,String d){
        String[] sit={
                "%s这回是下死手了，朝着%s的裤裆就是一勾拳，当即把他打倒在地上疼的打滚。 <br> ",
        };
        return String.format(sit[RandomUtil.randomInt(sit.length)],a,d);
    }


    String striStr(String a,String d,double dmg){
        String[] sit={
                "%s捏着锭子越想越怄不过，趁着%s发朋友圈的瞬间，就是一个火辣子夺了过去，对他造成了%.2f点反击伤害。 <br> ",
        };
        return String.format(sit[RandomUtil.randomInt(sit.length)],d,a,dmg);
    }

    String dmgStr(String a,String d,double dmg){
        String[] sit={
                "在这回合的交锋中%s对%s造成了%.2f点硬伤害。 <br> ",
        };
        return String.format(sit[RandomUtil.randomInt(sit.length)],a,d,dmg);
    }

    String douStr(String a,String d){
        String[] sit={
                "%s看到在地上惊叫唤的%s觉得应该乘胜追击。 <br> ",
        };
        return String.format(sit[RandomUtil.randomInt(sit.length)],a,d);
    }

    String currHealthStr(String a,double ah,String d,double dh){
        String[] sit={
                "当前%s还剩下【%.2f】点血，%s还剩下【%.2f】点血。 <br> ",
        };
        return String.format(sit[RandomUtil.randomInt(sit.length)],a,ah,d,dh);
    }

    String winStr(String a,String d ){
        String[] sit={
                "%s最终获得了胜利，轻蔑对地上失去意识的%s说：麻袋麻袋多内。 <br> ",
        };
        return String.format(sit[RandomUtil.randomInt(sit.length)],a,d);
    }

    String failStr(String a,String d ){
        String[] sit={
                "%s把%s按在地上摩擦，你只能跪在地上求他：爸爸我错了，不要打我了。 <br> ",
        };
        return String.format(sit[RandomUtil.randomInt(sit.length)],d,a);
    }

}
