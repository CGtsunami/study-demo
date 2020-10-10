package com.kafka.controller;

import com.kafka.config.KafkaListenerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @Autowired
    private KafkaListenerConfig kafkaListenerConfig;
    @GetMapping("/pub")
    public void   pub(@RequestParam("controlType") Integer controlType, @RequestParam("topic") String topic, @RequestParam("record") Integer record, @RequestParam("value") String value) {
        log.info("pub start, topic={},value={}", topic, value);
        long startTime = System.currentTimeMillis();
        //创建生产者
        Producer producer  = new KafkaProducer<>(kafkaListenerConfig.getProperties());
        for(int i=1;i<=record;i++) {
            ProducerRecord<String, String> msg = new ProducerRecord<String, String>(topic, value);
            producer.send(msg);
        }
        producer.close();
        log.info("pub over, topic={},value={}, escape={}", topic, value, System.currentTimeMillis()-startTime);

    }
}
