package com.sakurabird.cangxi.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.sakurabird.cangxi.entity.Users;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<Users> {

    @Update("UPDATE times_control set use_times=50 where use_type='luckDraw'")
    void flushLuck();

    @Update("UPDATE times_control set use_times=25 where use_type='fight'")
    void flushFight();
}
