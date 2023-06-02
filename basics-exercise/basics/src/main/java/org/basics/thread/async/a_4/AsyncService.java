package org.basics.thread.async.a_4;

/**
 * AsyncService
 *
 * @author ljf
 * @version 3.0
 * @date 2023/06/02 13:38
 **/
public interface AsyncService {

    MessageResult sendSms(String callPrefix, String mobile, String actionType, String content);

    MessageResult sendEmail(String email, String subject, String content);
}
