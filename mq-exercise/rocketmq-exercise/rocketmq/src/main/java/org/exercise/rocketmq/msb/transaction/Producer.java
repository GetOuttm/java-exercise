package org.exercise.rocketmq.msb.transaction;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

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
        TransactionMQProducer producer = new TransactionMQProducer("transaction");
        //设置namesrv地址
        producer.setNamesrvAddr("39.106.199.195:9876");

        //回调
        producer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                //执行本地事务
                System.out.println("=====executeLocalTransaction");
                System.out.println("msg:" + new String(message.getBody()));
                System.out.println("msg:" + message.getTransactionId());

                return LocalTransactionState.UNKNOW;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                //Broker端回调。检查事务

                System.out.println("=====checkLocalTransaction");
                System.out.println("msg:" + new String(messageExt.getBody()));
                System.out.println("msg:" + messageExt.getTransactionId());
                //事务执行成功
                //return LocalTransactionState.COMMIT_MESSAGE;

                //等会
                return LocalTransactionState.UNKNOW;
                //回滚消息
                //return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        });

        producer.start();

        Message message = new Message("transaction-demo-topic", "demo-transaction",
                "this is a trans message and lqs is freaking awesome!".getBytes(StandardCharsets.UTF_8));
        SendResult result = producer.sendMessageInTransaction(message, "事务demo参数");
        //返回结果
        System.out.println(result);
    }
}
