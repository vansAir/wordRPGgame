package com.sakurabird.cangxi.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sakurabird.cangxi.constvalue.GlobalConst;
import com.sakurabird.cangxi.dao.*;
import com.sakurabird.cangxi.entity.*;
import com.sakurabird.cangxi.manager.FateManager;
import com.sakurabird.cangxi.system.context.HttpContext;
import com.sakurabird.cangxi.system.data.exception.UserException;
import com.sakurabird.cangxi.system.enums.ErrorCode;
import com.sakurabird.cangxi.system.util.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
*@author vsans@sina.cn
*@date 2021/2/1
*@desc 用户服务
**/
@Service
public class UserService extends ServiceImpl<UserMapper, Users> {

    @Autowired
    UserMapper userMapper;

    @Autowired
    FateMapper fateMapper;

    @Autowired
    EquipmentMapper equipmentMapper;

    @Autowired
    UserEquipmentMapper userEquipmentMapper;

    @Autowired
    SkillMapper skillMapper;

    @Autowired
    TimesControlMapper timesControlMapper;

    @Autowired
    AreaMapper areaMapper;

    @Autowired
    EquiService equiService;

    @Autowired
    SkillService skillService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
    *@author vsans@sina.cn
    *@date 2021/2/1
    *@params
    *@return
    *@desc 用户注册方法
    **/
    public void regist(Users users) {
        //用空白的用户名挑战道德的底线
        if(StrUtil.isBlank(users.getUserName())||StrUtil.isBlank(users.getUserPassword())
                ||StrUtil.isBlank(users.getPrefixName())||StrUtil.isBlank(users.getRealName())
                ||StrUtil.isBlank(users.getArea())){
            throw new UserException(ErrorCode.USER_INFO_BLANK);
        }

        //用短细软挑战道德的底线
        if(users.getUserName().length()<4||users.getUserPassword().length()<6
                ||users.getPrefixName().length()<2||users.getRealName().length()<2){
            throw new UserException(ErrorCode.INFO_TOO_SHORT);
        }
        //检查用户名是是否重复
        checkName(users.getUserName(),users.getPrefixName(),users.getRealName());
        //处理数据
        handleData(users);
        userMapper.insert(users);
        //修改装备所属的用户ID
        equiService.updateEquiUserId(Integer.parseInt(users.getHead()),users.getId());
        equiService.updateEquiUserId(Integer.parseInt(users.getArms()),users.getId());
        equiService.updateEquiUserId(Integer.parseInt(users.getBody()),users.getId());
        equiService.updateEquiUserId(Integer.parseInt(users.getOrnaments()),users.getId());
        equiService.updateEquiUserId(Integer.parseInt(users.getShoes()),users.getId());
        //添加抽奖次数
        TimesControl ld=new TimesControl();
        ld.setUserId(users.getId());
        ld.setUseTimes(50);
        ld.setUseType("luckDraw");
        timesControlMapper.insert(ld);
        TimesControl ld1=new TimesControl();
        ld1.setUserId(users.getId());
        ld1.setUseTimes(25);
        ld1.setUseType("fight");
        timesControlMapper.insert(ld1);
    }


    /**
    *@author vsans@sina.cn
    *@date 2021/2/1
    *@params
    *@return
    *@desc 用户登录方法
    **/
    public String login(Users users){
        QueryWrapper<Users> exit= new QueryWrapper<>();
        exit.eq("user_name",users.getUserName());
        exit.eq("user_password",users.getUserPassword());
        Users user =  userMapper.selectOne(exit);
        if(user==null){
            throw new UserException(ErrorCode.USER_LOGIN_ERROR);
        }
        assembleData(user);
        String token= RSAUtil.encrypt(user.getId().toString());
        redisTemplate.boundValueOps(token).set("",60*60*8, TimeUnit.SECONDS);
        computeData(user.getId());
        return token;
    }

    /**
    *@author vsans@sina.cn
    *@date 2021/2/2
    *@params
    *@return
    *@desc 查看用户详细信息
    **/
    public Users getUserInfo(){
        Integer id=HttpContext.getId();
        computeData();
        Users users=JSONUtil.toBean(redisTemplate.boundValueOps(GlobalConst.USDATA+id).get().toString(),Users.class);
        assembleData(users);
        return users;
    }

    public Set<UserPhbInfo> getPhb(){
        return redisTemplate.opsForZSet().rangeWithScores("RANK",0,-1).stream().map(e->{
            UserPhbInfo up=new UserPhbInfo();
            up.setId(Integer.parseInt(e.getValue().toString()));
            up.setCombat(Double.parseDouble(e.getScore().toString()));
            userMapper.selectList(null).stream().forEach(es->{
                if(es.getId()==Integer.parseInt(e.getValue().toString())){
                    up.setRealName(es.getRealName());
                }
            });
            return up;
        }).collect(Collectors.toSet());
    }

    public void ooo(int rank,String rankstr){
        Equipment eq=new Equipment();
        eq.setEquipmentName("");
        eq.setAttack(0.0*rank);
        eq.setDefense(50.0*rank);
        eq.setLucky(10.0*rank);
        eq.setSpeed(0.0*rank);
        eq.setHealth(50.0*rank);
        eq.setThief(0.0*rank);
        eq.setCriticalHit(0.0*rank);
        eq.setStrikeBack(0.5*rank);
        eq.setDoubleHit(0.0*rank);
        eq.setKillProbability(0.0*rank);
        eq.setMissProbability(0.0*rank);
        eq.setAttackProbability(0.0*rank);
        eq.setEquType(1);
        eq.setEquRank(rankstr);
        eq.setMoney(0.0);
        eq.setEquDescribe("");
        equipmentMapper.insert(eq);

        Equipment eq1=new Equipment();
        eq1.setEquipmentName("");
        eq1.setAttack(0.0*rank);
        eq1.setDefense(100.0*rank);
        eq1.setLucky(10.0*rank);
        eq1.setSpeed(0.0*rank);
        eq1.setHealth(100.0*rank);
        eq1.setThief(0.0*rank);
        eq1.setCriticalHit(0.0*rank);
        eq1.setStrikeBack(0.0*rank);
        eq1.setDoubleHit(0.0*rank);
        eq1.setKillProbability(0.0*rank);
        eq1.setMissProbability(0.0*rank);
        eq1.setAttackProbability(0.0*rank);
        eq1.setEquType(2);
        eq1.setEquRank(rankstr);
        eq1.setMoney(0.0);
        eq1.setEquDescribe("");
        equipmentMapper.insert(eq1);

        Equipment eq3=new Equipment();
        eq3.setEquipmentName("");
        eq3.setAttack(0.0*rank);
        eq3.setDefense(0.0*rank);
        eq3.setLucky(0.0*rank);
        eq3.setSpeed(30.0*rank);
        eq3.setHealth(0.0*rank);
        eq3.setThief(0.5*rank);
        eq3.setCriticalHit(0.0*rank);
        eq3.setStrikeBack(0.0*rank);
        eq3.setDoubleHit(0.5*rank);
        eq3.setKillProbability(0.0*rank);
        eq3.setMissProbability(1.0*rank);
        eq3.setAttackProbability(0.0*rank);
        eq3.setEquType(3);
        eq3.setEquRank(rankstr);
        eq3.setMoney(0.0);
        eq3.setEquDescribe("");
        equipmentMapper.insert(eq3);

        Equipment eq4=new Equipment();
        eq4.setEquipmentName("");
        eq4.setAttack(50.0*rank+150);
        eq4.setDefense(0.0*rank);
        eq4.setLucky(0.0*rank);
        eq4.setSpeed(0.0*rank);
        eq4.setHealth(0.0*rank);
        eq4.setThief(0.0*rank);
        eq4.setCriticalHit(1.0*rank);
        eq4.setStrikeBack(0.0*rank);
        eq4.setDoubleHit(0.5*rank);
        eq4.setKillProbability(0.0*rank);
        eq4.setMissProbability(0.0*rank);
        eq4.setAttackProbability(0.0*rank);
        eq4.setEquType(4);
        eq4.setEquRank(rankstr);
        eq4.setMoney(0.0);
        eq4.setEquDescribe("");
        equipmentMapper.insert(eq4);

        Equipment eq5=new Equipment();
        eq5.setEquipmentName("");
        eq5.setAttack(0.0*rank);
        eq5.setDefense(0.0*rank);
        eq5.setLucky(0.0*rank);
        eq5.setSpeed(0.0*rank);
        eq5.setHealth(0.0*rank);
        eq5.setThief(0.5*rank);
        eq5.setCriticalHit(0.0*rank);
        eq5.setStrikeBack(0.0*rank);
        eq5.setDoubleHit(0.0*rank);
        eq5.setKillProbability(1.0*rank);
        eq5.setMissProbability(0.0*rank);
        eq5.setAttackProbability(1.0*rank);
        eq5.setEquType(5);
        eq5.setEquRank(rankstr);
        eq5.setMoney(0.0);
        eq5.setEquDescribe("");
        equipmentMapper.insert(eq5);

    }

    /**
    *@author vsans@sina.cn
    *@date 2021/2/1
    *@params
    *@return
    *@desc 用户注册数据处理方法
    **/
    private void handleData(Users users){
        users.setId(null);
        users.setUserName(StrUtil.cleanBlank(users.getUserName()));
        users.setUserPassword(users.getUserPassword());
        users.setPrefixName(StrUtil.cleanBlank(users.getPrefixName()));
        users.setRealName(StrUtil.cleanBlank(users.getRealName()));
        users.setArea(StrUtil.cleanBlank(users.getArea()));

        //答题增益
        answerProfit(users);

        //生成初始头盔
        users.setHead(equiService.randomEquipment(1,"N").getId().toString());
        //生成初始衣服
        users.setBody(equiService.randomEquipment(2,"N").getId().toString());
        //生成初始鞋子
        users.setShoes(equiService.randomEquipment(3,"N").getId().toString());
        //生成初始武器
        users.setArms(equiService.randomEquipment(4,"N").getId().toString());
        //生成初始饰品
        users.setOrnaments(equiService.randomEquipment(5,"N").getId().toString());
        //初始技能
        users.setSkill(skillService.randomSkill("N").getId());

        //随机天命
        List<Fate> fates=fateMapper.selectList(null);
        users.setFate(String.valueOf(fates.get(Math.abs((users.getPrefixName()+users.getRealName()).hashCode()%fates.size())).getId()));


    }

    /**
    *@author vsans@sina.cn
    *@date 2021/2/1
    *@params
    *@return
    *@desc 组装user数据
    **/
    private void assembleData(Users users){

        QueryWrapper<Skill> skill=new QueryWrapper<>();
        skill.eq("id",users.getSkill());
        users.setSkill(skillMapper.selectOne(skill).getSkillName());

        QueryWrapper<Area> area=new QueryWrapper<>();
        area.eq("id",users.getArea());
        users.setArea(areaMapper.selectOne(area).getAreaName());

        QueryWrapper<Fate> fate=new QueryWrapper<>();
        fate.eq("id",users.getFate());
        Fate ft=fateMapper.selectOne(fate);
        users.setFate(ft.getFateName());
        users.setFateDesc(ft.getFateDesc());

        users.setUserPassword("");

    }

    /**
     *@author vsans@sina.cn
     *@date 2021/2/2
     *@params
     *@return
     *@desc 计算用户当前属性值
     **/
    public void  computeData(){
        Integer id=HttpContext.getId();
        computeData(id);
    }


    /**
    *@author vsans@sina.cn
    *@date 2021/2/2
    *@params
    *@return
    *@desc 计算用户当前属性值
    **/
    public void  computeData(Integer id){
        Users cunrrentUser=userMapper.selectById(id);
        cunrrentUser.setUserPassword("");

        //计算装备属性
        computeEqui(cunrrentUser);
        //计算地区属性
        computeArea(cunrrentUser);
        //计算天命属性
        computeFate(cunrrentUser);
        //绑定到redis
        redisTemplate.boundValueOps(GlobalConst.USDATA +id).set(JSONUtil.parse(cunrrentUser).toString());
        DecimalFormat df = new DecimalFormat("0.00");
        double combat=cunrrentUser.getAttack()+cunrrentUser.getDefense()+cunrrentUser.getHealth()+cunrrentUser.getSpeed()/10+cunrrentUser.getLucky()/3
                +cunrrentUser.getAttackProbability()*100.0+cunrrentUser.getMissProbability()*100.0+cunrrentUser.getKillProbability()*100.0+cunrrentUser.getCriticalHit()*100.0
                +cunrrentUser.getStrikeBack()*100.0+cunrrentUser.getDoubleHit()*100.0;
        redisTemplate.opsForZSet().add("RANK", cunrrentUser.getId(), combat);

    }

    private void computeEqui(Users cunrrentUser){
        QueryWrapper<UserEquipment> ue=new QueryWrapper<>();
        ue.eq("user_id",cunrrentUser.getId());
        List<UserEquipment> ues=userEquipmentMapper.selectList(ue);

        //属性计算点
        ues.stream().forEach(
                e->{
                    double x=Math.pow((e.getLevel()*0.04+1),2);
                    cunrrentUser.setAttack(e.getAttack()*x+cunrrentUser.getAttack());
                    cunrrentUser.setDefense(e.getDefense()*x+cunrrentUser.getDefense());
                    cunrrentUser.setHealth(e.getHealth()*x+cunrrentUser.getHealth());
                    cunrrentUser.setSpeed(e.getSpeed()*x+cunrrentUser.getSpeed());
                    cunrrentUser.setLucky(e.getLucky()*x+cunrrentUser.getLucky());
                    cunrrentUser.setCriticalHit(e.getCriticalHit()*x+cunrrentUser.getCriticalHit());
                    cunrrentUser.setStrikeBack(e.getStrikeBack()*x+cunrrentUser.getStrikeBack());
                    cunrrentUser.setDoubleHit(e.getDoubleHit()*x+cunrrentUser.getDoubleHit());
                    cunrrentUser.setKillProbability(e.getKillProbability()*x+cunrrentUser.getKillProbability());
                    cunrrentUser.setThief(e.getThief()*x+cunrrentUser.getThief());
                    cunrrentUser.setAttackProbability(e.getAttackProbability()*x+cunrrentUser.getAttackProbability());
                    cunrrentUser.setMissProbability(e.getMissProbability()*x+cunrrentUser.getMissProbability());
                }
        );
    }

    private void computeArea(Users cunrrentUser){
        //属性计算点
        QueryWrapper<Area> ar=new QueryWrapper<>();
        ar.eq("id",cunrrentUser.getArea());
        Area area=areaMapper.selectOne(ar);
        cunrrentUser.setAttack(area.getAttack()*cunrrentUser.getAttack());
        cunrrentUser.setDefense(area.getDefense()*cunrrentUser.getDefense());
        cunrrentUser.setHealth(area.getHealth()*cunrrentUser.getHealth());
        cunrrentUser.setSpeed(area.getSpeed()*cunrrentUser.getSpeed());
        cunrrentUser.setLucky(area.getLucky()*cunrrentUser.getLucky());
        cunrrentUser.setCriticalHit(area.getCriticalHit()*cunrrentUser.getCriticalHit());
        cunrrentUser.setStrikeBack(area.getStrikeBack()*cunrrentUser.getStrikeBack());
        cunrrentUser.setDoubleHit(area.getDoubleHit()*cunrrentUser.getDoubleHit());
        cunrrentUser.setKillProbability(area.getKillProbability()*cunrrentUser.getKillProbability());
        cunrrentUser.setThief(area.getThief()*cunrrentUser.getThief());
        cunrrentUser.setAttackProbability(area.getAttackProbability()*cunrrentUser.getAttackProbability());
        cunrrentUser.setMissProbability(area.getMissProbability()*cunrrentUser.getMissProbability());
    }

    private void computeFate(Users cunrrentUser){
        QueryWrapper<Fate> fa=new QueryWrapper<>();
        fa.eq("id",cunrrentUser.getFate());
        Fate fate=fateMapper.selectOne(fa);
        FateManager.computeFate(fate,cunrrentUser);
    }

    /**
    *@author vsans@sina.cn
    *@date 2021/2/2
    *@params
    *@return
    *@desc 答题收益计算
    **/
    private void answerProfit(Users users){

        //概率检测防止用户修改答题数据
        Double t=users.getThief();
        Double l=users.getLucky();
        Double d=users.getDoubleHit();
        Double s=users.getStrikeBack();
        Double c=users.getCriticalHit();
        if(((t+d+s+c)*10+l)>25){
            throw new UserException(ErrorCode.USER_ERROR_ANSWER);
        }

        //用户初始数据生成
        //属性计算点
        users.setMoney(3000.00);
        users.setAttack(RandomUtil.randomDouble(80.00,120.00));
        users.setDefense(RandomUtil.randomDouble(40.00,80.00));
        users.setSpeed(RandomUtil.randomDouble(40.00,80.00));
        users.setLucky(l+RandomUtil.randomDouble(10.00,20.00));
        users.setHealth(RandomUtil.randomDouble(2800.00,3200.00));
        users.setKillProbability(RandomUtil.randomDouble(0.1,0.5));
        users.setDoubleHit(d+RandomUtil.randomDouble(1.00,2.00));
        users.setCriticalHit(c+RandomUtil.randomDouble(1.00,2.00));
        users.setStrikeBack(s+RandomUtil.randomDouble(1.00,2.00));
        users.setThief(t+RandomUtil.randomDouble(1.00,2.00));
        users.setAttackProbability(RandomUtil.randomDouble(1.00,2.00));
        users.setMissProbability(RandomUtil.randomDouble(1.00,2.00));
    }

    /**
    *@author vsans@sina.cn
    *@date 2021/2/2
    *@params
    *@return
    *@desc 获取所有的地址
    **/
    public List<Area> getArea(){
        return areaMapper.selectList(null);
    }

    /**
    *@author vsans@sina.cn
    *@date 2021/2/2
    *@params
    *@return
    *@desc 检查用户名,前缀和名称是否重复
    **/
    public void checkName(String userName,String prefixName,String realName){
        QueryWrapper<Users> exit= new QueryWrapper<>();
        exit.eq("user_name",userName);
        //已注册
        if(userMapper.selectOne(exit)!=null){
            throw new UserException(ErrorCode.REPEAT_REGISTER);
        }
        QueryWrapper<Users> exit1= new QueryWrapper<>();
        exit1.eq("prefix_name",prefixName);
        exit1.eq("real_name",realName);
        //名称已注册
        if(userMapper.selectOne(exit1)!=null){
            throw new UserException(ErrorCode.REPEAT_REGISTER);
        }
    }
}
