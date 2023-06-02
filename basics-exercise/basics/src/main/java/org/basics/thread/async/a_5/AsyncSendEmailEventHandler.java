package org.basics.thread.async.a_5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;

/**
 * AsyncSendEmailEventHandler 时间处理器
 *
 * @author ljf
 * @version 3.0
 * @date 2023/06/02 13:50
 **/
public class AsyncSendEmailEventHandler implements ApplicationListener<AsyncSendEmailEvent> {

    /**
     * 另外，可能有些时候采用ApplicationEvent实现异步的使用，当程序出现异常错误的时候，需要考虑补偿机制，
     * 那么这时候可以结合Spring Retry重试来帮助我们避免这种异常造成数据不一致问题。
     */

    @Autowired
    private IMessageHandler messageHandler;

    @Async("takExecutor")
    @Override
    public void onApplicationEvent(AsyncSendEmailEvent event) {
        if (event == null) {
            return;
        }
        String email = event.getEmail();
        String subject = event.getSubject();
        String content = event.getContent();
        String targetUserId = event.getTargetUserId();

        messageHandler.sendsendEmailSms(email, subject, content, targetUserId);
    }


}
