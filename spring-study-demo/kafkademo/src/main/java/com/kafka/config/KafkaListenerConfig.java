package com.kafka.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @ClassName KafkaListenerConfig
 * @Description kafka配置
 * @Date 2020/8/6
 * @Version 1.0
 */
@Slf4j
@Configuration
public class KafkaListenerConfig {
    @Value("${kafka.bootstrap-servers:}")
    private String servers;

    @Value("${kafka.consumer.kafka.auto.offset.reset:earliest}")
    private String offsetReset;

    @Value("${kafka.consumer.enable-auto-commit:true}")
    private String autoCommit;

    @Value("${kafka.consumer.auto-commit-interval:1000}")
    private String commitInterval;

    @Value("${kafka.consumer.max.poll.records:1000}")
    private String maxPollRecords;

    @Value("${kafka.consumer.key-deserializer:org.apache.kafka.common.serialization.StringDeserializer}")
    private String keyDeserializer;

    @Value("${kafka.consumer.value-deserializer:org.apache.kafka.common.serialization.StringDeserializer}")
    private String valueDeserializer;

    @Value("${kafka.producer.value-serializer:org.apache.kafka.common.serialization.StringSerializer}")
    private String keySerializer;

    @Value("${kafka.producer.key-serializer:org.apache.kafka.common.serialization.StringSerializer}")
    private String valueSerializer;

    @Value("${kafka.consumer.properties.sasl.mechanism:GSSAPI}")
    private String mechanism;

    @Value("${kafka.consumer.properties.security.protocol:SASL_PLAINTEXT}")
    private String protocol;

    public Properties getProperties() {
        log.info("init kafka listener");
        Properties props = new Properties();
        props.put("bootstrap.servers", servers);
        props.put("auto.offset.reset", offsetReset);
        props.put("enable.auto.commit", autoCommit);
        props.put("auto.commit.interval.ms", commitInterval);
        props.put("max.poll.records", maxPollRecords);
        props.put("key.deserializer", keyDeserializer);
        props.put("value.deserializer", valueDeserializer);
        props.put("key.serializer", keySerializer);
        props.put("value.serializer", valueSerializer);
        props.put("sasl.mechanism",mechanism);
        props.put("security.protocol",protocol);
        return props;
    }

}