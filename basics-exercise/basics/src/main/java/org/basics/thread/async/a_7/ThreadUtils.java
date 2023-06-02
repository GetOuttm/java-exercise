package org.basics.thread.async.a_7;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadLocalRandom;

/**
 * ThreadUtils ThreadUtil异步工具类
 *
 * @author ljf
 * @version 3.0
 * @date 2023/06/02 13:58
 **/
@Slf4j
public class ThreadUtils {

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            ThreadUtil.execAsync(() -> {
                ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
                int number = threadLocalRandom.nextInt(20) + 1;
                System.out.println(number);
            });
            log.info("当前第：" + i + "个线程");
        }

        log.info("task finish!");
    }
}