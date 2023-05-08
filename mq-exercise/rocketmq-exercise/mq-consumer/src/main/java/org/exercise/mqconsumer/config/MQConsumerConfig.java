package org.exercise.mqconsumer.config;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
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
public class MQConsumerConfig {

    private static final Logger logger = LoggerFactory.getLogger(MQConsumerConfig.class);

    @Bean
    public DefaultMQPushConsumer getRocketMqProducer() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("xxxx");
        consumer.setNamesrvAddr("39.106.199.195:9876");
        consumer.subscribe("springboot", "*");
        // consumer.registerMessageListener(new MessageListenerConcurrently() {
        //     @Override
        //     public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        //         System.out.println("来了老弟");
        //         return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        //     }
        // });
        //在新上线一个consumer   从哪消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //一次从broker拉去多少条信息 默认32条
        consumer.setPullBatchSize(32);
        // 一次往listener中推多少条   默认2000
        // consumer.setConsumeConcurrentlyMaxSpan();

        consumer.registerMessageListener(new MyMessageListener());
        consumer.start();
        logger.info("MQRocket Consumer Start");
        return consumer;
    }
}
