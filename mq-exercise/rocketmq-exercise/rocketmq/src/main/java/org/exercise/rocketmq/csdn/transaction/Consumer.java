package org.exercise.rocketmq.csdn.transaction;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @NAME: Consumer
 * @USER: ljf
 * @TIME: 2023/2/26  17:44
 * @REMARK:
 **/
public class Consumer {

    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("transaction-demo");
        consumer.setNamesrvAddr("39.106.199.195:9876");
        consumer.subscribe("transaction-demo-topic", "demo-transaction");

        //实现消息监听接口
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                try {
                    list.forEach(l -> {
                        //获取消息
                        System.out.println("body: " + new String(l.getBody()));
                        System.out.println("context: " + consumeConcurrentlyContext);
                    });
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                } catch (Exception e) {
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
        });
        consumer.start();
        System.out.println("消息启动成功");
    }
}
