package org.exercise.mqproducer.config;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author ljf
 * @Date 2023/3/10 13:27
 * @Description
 */
@Configuration
public class MQProducerConfig {

    private static final Logger logger = LoggerFactory.getLogger(MQProducerConfig.class);

    @Bean
    public DefaultMQProducer getRocketMqProducer() throws Exception {
        //创建生产者
        DefaultMQProducer producer = new DefaultMQProducer("xxxx");
        //设置namesrv地址
        producer.setNamesrvAddr("39.106.199.195:9876");
        producer.start();
        logger.info("RocketMQ Producer Start");
        return producer;
    }
}
