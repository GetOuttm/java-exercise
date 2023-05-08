package org.exercise.rocketmq.msb.order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
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
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name");
        consumer.setNamesrvAddr("39.106.199.195:9876");
        // 订阅需要消费的主题和标签下的消息
        consumer.subscribe("TopicTest", "*");
        // 实现消息监听接口
        /**
         * MessageListenerConcurrently 会并发消费/开多线程
         *
         */
        // consumer.registerMessageListener(new MessageListenerConcurrently() {
        //     @Override
        //     public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        //         try {
        //             list.forEach(l -> {
        //                 // 获取消息
        //                 System.out.println("body: " + new String(l.getBody()));
        //                 System.out.println("context: " + consumeConcurrentlyContext);
        //             });
        //             return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        //         } catch (
        //                 Exception e) {
        //             return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        //         }
        //     }
        // });

        //最大开启消费线程数
        // consumer.setConsumeThreadMax();
        //最小开启消费线程数
        // consumer.setConsumeThreadMin();

        //MessageListenerOrderly   顺序消费，对一个queue开启一个线程，多个queue开启多个线程
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                try {
                    list.forEach(l -> {
                        // 获取消息
                        System.out.println("body: " + new String(l.getBody()));
                        System.out.println("context: " + consumeOrderlyContext);
                    });
                    return ConsumeOrderlyStatus.SUCCESS;
                } catch (
                        Exception e) {
                    return ConsumeOrderlyStatus.SUCCESS;
                }
            }
        });
        consumer.start();
        System.out.println("消息启动成功");
    }
}
