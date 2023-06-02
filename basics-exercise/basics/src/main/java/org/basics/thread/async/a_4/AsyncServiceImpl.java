package org.basics.thread.async.a_4;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * AsyncServiceImpl
 *
 * @author ljf
 * @version 3.0
 * @date 2023/06/02 13:41
 **/
@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService {

    /**
     * 在实际项目中， 使用@Async调用线程池，推荐等方式是是使用自定义线程池的模式，不推荐直接使用@Async直接实现异步。
     */

    @Autowired
    private IMessageHandler messageHandler;

    @Override
    @Async("taskExecutor")
    public MessageResult sendSms(String callPrefix, String mobile, String actionType, String content) {
        try {
            Thread.sleep(1000);
            messageHandler.sendSms(callPrefix, mobile, actionType, content);
        } catch (Exception e) {
            log.error("发送短信异常 -> ", e);
        }
    }

    @Override
    @Async("taskExecutor")
    public MessageResult sendEmail(String email, String subject, String content) {
        try {
            Thread.sleep(1000);
            messageHandler.sendEmail(email, subject, content);
        } catch (Exception e) {
            log.error("发送email异常 -> ", e);
        }
    }
}
