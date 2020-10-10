package com.kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.jboss.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

/**
 * @author jxy
 * @ClassName PcEngineProcessor
 * @Description 卡口过车匹配逻辑处理类
 * @Date 2020/9/21
 * @Version 1.0
 */
@Service
@Slf4j
@Transactional
public class PcEngineProcessor {

    private static final String KKWZ_KEY = "PAP:MONITOR:";
    private static final String HPHM_KEY = "HPHM";
    private static final String GCSJ_KEY = "GCSJ";
    private static final String DWBH_KEY = "DWBH";
    private static final String ADDRESS_KEY = "address";
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";


    /**
     * 批量处理接入数据
     * @param consumerRecords 接入数据
     */
    @Async
    public void batchProcess( ConsumerRecords<String, String> consumerRecords){
        //处理数据
        for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
            try {
                log.debug("createListener receive record. targetControlType={}, topic={}, consumerRecords={}", consumerRecord.topic(), consumerRecords);
                //kafka获取的数据转换成string
               // process(JsonUtil.toMap(consumerRecord.value()) );
            } catch (Exception e) {
                log.error("ControlEngineListener parse record error. update record, consumerRecords={}", consumerRecords, e);
            }
        }
    }

    /**
     * 处理过车数据匹配关键人车
     * @param record 接入数据
     */
    private void process(Map<String, Object> record){


    }




}
