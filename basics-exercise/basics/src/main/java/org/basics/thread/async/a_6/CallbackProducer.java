package org.basics.thread.async.a_6;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * CallbackProducer   回调时间消息生产者
 *
 * @author ljf
 * @version 3.0
 * @date 2023/06/02 13:55
 **/
@Slf4j
@Component
public class CallbackProducer {

    @Autowired
    AmqpTemplate amqpTemplate;

    public void sendCallbackMessage(CallbackDTO allbackDTO, final long delayTimes) {

        log.info("生产者发送消息，callbackDTO，{}", callbackDTO);

        amqpTemplate.convertAndSend(CallbackQueueEnum.QUEUE_GENSEE_CALLBACK.getExchange(), CallbackQueueEnum.QUEUE_GENSEE_CALLBACK.getRoutingKey(), JsonMapper.getInstance().toJson(genseeCallbackDTO), new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //给消息设置延迟毫秒值，通过给消息设置x-delay头来设置消息从交换机发送到队列的延迟时间
                message.getMessageProperties().setHeader("x-delay", delayTimes);
                message.getMessageProperties().setCorrelationId(callbackDTO.getSdkId());
                return message;
            }
        });
    }
}
