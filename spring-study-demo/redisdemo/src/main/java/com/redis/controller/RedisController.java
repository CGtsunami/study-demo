package com.redis.controller;

import com.redis.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisController {
    private static int ExpireTime = 60;   // redis中存储的过期时间60s
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("set")
    public boolean redisset(String key,String value){
        return redisUtil.set(key,value,ExpireTime);
    }

    @GetMapping("get")
    public Object redisget(String key){
        return redisUtil.get(key);
    }

    @PostMapping("expire")
    public boolean expire(String key){
        return redisUtil.expire(key,ExpireTime);
    }
}
