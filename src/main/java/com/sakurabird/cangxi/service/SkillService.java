package com.sakurabird.cangxi.service;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sakurabird.cangxi.dao.SkillMapper;
import com.sakurabird.cangxi.entity.Equipment;
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
public class SkillService {

    @Autowired
    SkillMapper skillMapper;

    public Skill randomSkill(String rank){
        QueryWrapper<Skill> equi=new QueryWrapper<>();
        equi.eq("skill_rank",rank);
        List<Skill> skills=skillMapper.selectList(equi);
        return skills.get(RandomUtil.randomInt(skills.size()));
    }

}
