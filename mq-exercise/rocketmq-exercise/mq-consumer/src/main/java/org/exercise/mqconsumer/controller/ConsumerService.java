package org.exercise.mqconsumer.controller;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author ljf
 * @Date 2023/3/9 14:00
 * @Description
 */
public class ConsumerService {

    @Autowired
    DefaultMQPushConsumer config;

    public void recive() throws Exception {
    }
}
