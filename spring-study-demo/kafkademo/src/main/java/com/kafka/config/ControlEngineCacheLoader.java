package com.kafka.config;

import com.kafka.listener.PcEngineListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @ClassName ControlEngineCacheLoader
 * @Description 初始化配置
 * @Date 2020/8/6
 * @Version 1.0
 */
@Component
@Slf4j
public class ControlEngineCacheLoader implements CommandLineRunner {

    @Autowired
    private PcEngineListener pcEngineListener;
    @Value("${pap.kafka.run:true}")
    private Boolean run;
    @Value("${java.security.krb5.conf:true}")
    private String krb5Conf;
    @Value("${java.security.auth.login.config:true}")
    private String loginConfig;

    @Override
    public void run(String... args) throws Exception {
        if(run) {

            //加载卡口过车数据
            pcEngineListener.createListener();
        }
    }
}
