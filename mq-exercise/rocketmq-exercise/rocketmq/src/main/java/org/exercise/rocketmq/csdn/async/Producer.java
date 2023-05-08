package org.exercise.rocketmq.csdn.async;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

/**
 * @NAME: Producer
 * @USER: ljf
 * @TIME: 2023/2/26  17:23
 * @REMARK: 异步生产者发送消息
 **/
public class Producer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("async-demo");
        producer.setNamesrvAddr("39.106.199.195:9876");
        producer.start();

        Message message = new Message("sync-demo-topic", "demo-async",
                "this is a async message and lqs is freaking awesome!".getBytes(StandardCharsets.UTF_8));
        // 设置延时(过了一定时间再发送到MQ对列) 5秒
        //  message.setDelayTimeLevel(2);// 指定延迟等级 比如2 表示5秒

        //异步回调获取返回结果集
        producer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送结果： " + sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("失败原因： " + throwable);
            }
        });
    }
}
