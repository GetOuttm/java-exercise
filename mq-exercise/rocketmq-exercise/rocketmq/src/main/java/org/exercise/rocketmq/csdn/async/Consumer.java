package org.exercise.rocketmq.csdn.async;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @NAME: Consumer
 * @USER: ljf
 * @TIME: 2023/2/26  17:28
 * @REMARK: 异步消费者
 **/
public class Consumer {

    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("async-demo");
        consumer.setNamesrvAddr("39.106.199.195:9876");
        consumer.subscribe("sync-demo-topic", "demo-async");

        // 实现消息监听接口
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                try {
                    list.forEach(l -> {
                        //获取消息
                        System.out.println("body: " + new String(l.getBody()));
                        System.out.println("context :" + consumeConcurrentlyContext);
                    });
                    //返回状态表示消息已经被消费，不可再消费
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                } catch (Exception e) {
                    //消息失败，可在此消息
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
        });
        consumer.start();
        System.out.println("消息启动成功");
    }
}
