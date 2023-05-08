package org.exercise.rocketmq.msb.transaction;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * @NAME: Demo
 * @USER: ljf
 * @TIME: 2023/2/26  12:43
 * @REMARK: 生产者
 **/
public class Producer1 {

    public static void main(String[] args) throws Exception {
        //创建生产者
        DefaultMQProducer producer = new DefaultMQProducer("xxxx");
        //设置namesrv地址
        producer.setNamesrvAddr("39.106.199.195:9876");
        producer.start();

        //同步发送时，重试次数默认2
        producer.setRetryTimesWhenSendFailed(2);
        //异步发送时，重试次数默认2
        producer.setRetryTimesWhenSendAsyncFailed(2);
        //是否相其他broker发送请求  默认false
        producer.setRetryAnotherBrokerWhenNotStoreOK(true);
        Message message = new Message("MyTopic002", "demo", "xxoo 第一条".getBytes());
        SendResult result = producer.send(message);
        //返回结果
        System.out.println(result);
        //关闭生产者
        producer.shutdown();
    }
}
