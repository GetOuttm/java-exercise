package org.exercise.rocketmq.csdn.transaction;

import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

/**
 * @NAME: Producer
 * @USER: ljf
 * @TIME: 2023/2/26  17:38
 * @REMARK: 事务消息生产者
 **/
public class Producer {

    public static void main(String[] args) throws Exception {
        //创建生产者对象
        TransactionMQProducer producer = new TransactionMQProducer("transaction-demo");
        //连接namesrv
        producer.setNamesrvAddr("39.106.199.195:9876");
        //创建线程
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService = new ThreadPoolExecutor(1, 30, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2));
        producer.setExecutorService(executorService);
        //指定线程的事务监听
        producer.setTransactionListener(new MyTransactionCheckListener());
        //启动
        producer.start();

        Message message = new Message("transaction-demo-topic", "demo-transaction",
                "this is a trans message and lqs is freaking awesome!".getBytes(StandardCharsets.UTF_8));
        producer.sendMessageInTransaction(message, "事务demo参数");
    }
}
