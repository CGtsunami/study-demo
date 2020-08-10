package com.redis.controller;

import com.redis.dao.UserEntity;
import com.redis.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisController {
    private static int ExpireTime = 600;   // redis中存储的过期时间60s
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("set")
    public boolean redisset(String key,String value){
        return redisUtil.set(key,value);
    }
    @PostMapping("setExpireTime")
    public boolean redissetExpireTime(String key,String value){
        return redisUtil.set(key,value,ExpireTime);
    }
    @RequestMapping("setuser")
    public boolean redissetuser(String key, String value){
        UserEntity userEntity =new UserEntity();
        userEntity.setId(Long.valueOf(1));
        userEntity.setGuid(String.valueOf(1));
        userEntity.setName("zhangsan");
        userEntity.setAge(String.valueOf(20));
        userEntity.setCreateTime(new Date());

        return redisUtil.set(key,userEntity);

        //return redisUtil.set(key,value);
    }

    @PostMapping("get")
    public Object redisget(String key){
        return redisUtil.get(key);
    }

    @PostMapping("expire")
    public boolean expire(String key){
        return redisUtil.expire(key,ExpireTime);
    }

    @PostMapping("getExpire")
    public long getExpire(String key){
        long expire = redisUtil.getExpire(key);
        return expire;
    }
    @PostMapping("hasKey")
    public boolean hasKey(String key){
        boolean b = redisUtil.hasKey(key);
        return b;
    }
}
