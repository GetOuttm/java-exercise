package org.exercise.mqproducer.controller;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author ljf
 * @Date 2023/3/9 14:00
 * @Description
 */
@RestController
public class ProducerController {

    @Autowired
    DefaultMQProducer config;

    @RequestMapping("/send")
    public Object send() throws Exception {
        //topic 消息将要发送的地址
        //body 消息中想要发送的具体数据
        // 消息参数依次是主题、标签、发送的消息
        Message message = new Message("springboot", "demo", "xxoo 第二条".getBytes());

        //message中可以指定messagequeue
        new MessageQueue();
        //可以查询所有的queue信息
        new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                return null;
            }
        };


        //sendDefaultImpl call timeout 加超时时间
        return config.send(message);
    }
}
