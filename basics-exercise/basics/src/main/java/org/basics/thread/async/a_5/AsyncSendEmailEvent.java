package org.basics.thread.async.a_5;

import org.springframework.context.ApplicationEvent;

/**
 * AsyncSebdEmailEvent
 *
 * @author ljf
 * @version 3.0
 * @date 2023/06/02 13:47
 **/
public class AsyncSendEmailEvent extends ApplicationEvent {

    //邮箱
    private String email;

    //主题
    private String subject;

    //内容
    private String content;

    //接收者
    private String targetUserId;

    public AsyncSendEmailEvent(Object source) {
        super(source);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }
}
