package org.exercise.rocketmq.csdn.transaction;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @NAME: MyTransactionCheckListener
 * @USER: ljf
 * @TIME: 2023/2/26  17:35
 * @REMARK: 事务监听
 **/
public class MyTransactionCheckListener implements TransactionListener {

    /**
     * @param message
     * @param o       事物消息携带的额外参数
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            System.out.println("事务发布消息：" + message);
            System.out.println("事务参考(额外数据) :" + o);
            //事务返回状态
            return LocalTransactionState.COMMIT_MESSAGE;
        } catch (Exception e) {
            e.printStackTrace();
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        return null;
    }
}
