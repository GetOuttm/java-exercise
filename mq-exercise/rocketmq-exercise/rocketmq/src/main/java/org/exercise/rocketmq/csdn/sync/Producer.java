package org.exercise.rocketmq.csdn.sync;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * @NAME: Demo
 * @USER: ljf
 * @TIME: 2023/2/26  12:43
 * @REMARK: 生产者
 **/
public class Producer {

    public static void main(String[] args) throws Exception {
        //创建生产者
        DefaultMQProducer producer = new DefaultMQProducer("xxxx");
        //设置namesrv地址
        producer.setNamesrvAddr("39.106.199.195:9876");
        producer.start();

        //topic 消息将要发送的地址
        //body 消息中想要发送的具体数据
        // 消息参数依次是主题、标签、发送的消息
        Message message = new Message("MyTopic001", "demo", "xxoo 第二条".getBytes());
        //sendDefaultImpl call timeout 加超时时间
        SendResult result = producer.send(message, 10000);
        //返回结果
        System.out.println(result);
        System.out.println("消息状态：" + result.getSendStatus());
        System.out.println("消息id：" + result.getMsgId());
        System.out.println("消息queue：" + result.getMessageQueue());
        System.out.println("消息offset：" + result.getQueueOffset());
        //关闭生产者
        producer.shutdown();

        //单向消息不保证发送成功 效率高
        // producer.sendOneway(message);


        /**
         * topic
         * tags 用来过滤消息，消息分组
         * keys
         * body
         */
        // public Message(String topic, String tags, String keys, byte[] body)


        //https://www.cnblogs.com/2YSP/p/11616376.html
    }
}
