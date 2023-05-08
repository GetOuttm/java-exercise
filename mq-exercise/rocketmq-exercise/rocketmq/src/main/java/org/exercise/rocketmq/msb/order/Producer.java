package org.exercise.rocketmq.msb.order;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @NAME: a
 * @USER: ljf
 * @TIME: 2023/2/27  22:14
 * @REMARK: 顺序消息发送
 **/
public class Producer {

    public static void main(String[] args) throws UnsupportedEncodingException {
        try {
            DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
            //设置namesrv地址
            producer.setNamesrvAddr("39.106.199.195:9876");
            producer.start();

            String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};
            for (int i = 0; i < 100; i++) {
                int orderId = i % 10;
                Message msg =
                        new Message("TopicTest", tags[i % tags.length], "KEY" + i,
                                ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.send(msg,
                        //queue选择器，向topic中的那个queue去写消息
                        new MessageQueueSelector() {
                            /**
                             * @param mqs 当前topic里面包含的所有queue
                             * @param msg  具体要发的哪条消息
                             * @param arg   对应到send()里面的args
                             * @return
                             */
                            @Override
                            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                                Integer id = (Integer) arg;
                                int index = id % mqs.size();
                                return mqs.get(index);
                            }
                        },
                        //自定义参数
                        1, 10000);

                System.out.printf("%s%n", sendResult);
            }

            producer.shutdown();
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
