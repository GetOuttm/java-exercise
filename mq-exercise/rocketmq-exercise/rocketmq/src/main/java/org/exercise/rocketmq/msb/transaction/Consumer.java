package org.exercise.rocketmq.msb.transaction;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @NAME: Consumer
 * @USER: ljf
 * @TIME: 2023/2/26  13:18
 * @REMARK: 消费者
 **/
public class Consumer {

    public static void main(String[] args) throws Exception {
        // 创建消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("xxxx");
        consumer.setNamesrvAddr("39.106.199.195:9876");
        // 订阅需要消费的主题和标签下的消息
        consumer.subscribe("MyTopic001", "demo");
        // 实现消息监听接口
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                try {
                    list.forEach(l -> {
                        // 获取消息
                        System.out.println("body: " + new String(l.getBody()));
                        System.out.println("context: " + consumeConcurrentlyContext);
                    });
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                } catch (
                        Exception e) {
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            }
        });
        consumer.start();
        System.out.println("消息启动成功");
    }
}
