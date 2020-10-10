package com.kafka.listener;


import com.kafka.config.KafkaListenerConfig;
import com.kafka.service.PcEngineProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author jxy
 * @DateTime 2020/9/21 14:19
 */
@Service
@Scope("prototype")
@Slf4j
public class PcEngineListener {
    private static final String GROUP_ID_KEY = "group.id";
    private static final String GROUP_ID_VALUE = "PAP:PC";
    private static final List<String> NOTHING = Arrays.asList(new String[]{"NOTHING"});
    @Value("${pap.kafka.topic}")
    private String topic;
    @Value("${pap.kafka.run:true}")
    private Boolean run;

    @Autowired
    private KafkaListenerConfig kafkaListenerConfig;

    @Autowired
    private PcEngineProcessor pcEngineProcessor;

    public void createListener() {
        if (!run) {
            log.info("createListener not should start. targetControlType={}");
            return;
        }
        String groupId = GROUP_ID_VALUE ;
        Thread thread = new Thread(new PcEngineListener.PcKafkaListener(groupId));
        thread.setName(groupId);
        thread.start();
    }

    class PcKafkaListener implements Runnable {
        private String groupId;

        public PcKafkaListener(String groupId) {
            this.groupId = groupId;
        }

        @Override
        public void run() {
            Properties props = kafkaListenerConfig.getProperties();
            props.put(GROUP_ID_KEY, groupId);
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
            // 最开始的订阅列表
            consumer.subscribe(Arrays.asList(topic));
            log.info("createListener subscribe over. targetControlType={}, topics={}", topic);
            while (true) {
                //表示每1秒consumer就有机会去轮询一下订阅状态是否需要变更
                ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofMillis(1000));
                //处理数据
                pcEngineProcessor.batchProcess(consumerRecords);
                //异步提交offset
                consumer.commitAsync();

            }
        }
    }

}
