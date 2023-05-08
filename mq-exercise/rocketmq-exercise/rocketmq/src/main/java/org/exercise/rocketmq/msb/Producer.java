package org.exercise.rocketmq.msb;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

/**
 * @NAME: Demo
 * @USER: ljf
 * @TIME: 2023/2/26  12:43
 * @REMARK: 生产者
 **/
public class Producer {

    public static void main(String[] args) throws Exception {
        //创建生产者
        DefaultMQProducer producer = new DefaultMQProducer("xxoog01");
        //设置namesrv地址
        producer.setNamesrvAddr("39.106.199.195:9876");
        producer.start();

        Message message = new Message("topic01", "demo-transaction",
                "this is a trans message and lqs is freaking awesome!".getBytes(StandardCharsets.UTF_8));
        SendResult result = producer.send(message);
        //返回结果
        System.out.println(result);
    }
}
