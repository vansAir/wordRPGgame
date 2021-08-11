package com.sakurabird.cangxi.system.config;

import com.sakurabird.cangxi.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchTask {

    @Autowired
    UserMapper userMapper;

    @Scheduled(cron = "0 0 8 * * ? ")
    public void execute() {
        userMapper.flushLuck();
        userMapper.flushFight();
    }
}
