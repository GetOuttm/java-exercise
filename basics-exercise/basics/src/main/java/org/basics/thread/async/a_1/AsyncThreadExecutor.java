package org.basics.thread.async.a_1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * AsyncThreadExecutor 优化异步线程
 *
 * @author ljf
 * @version 3.0
 * @date 2023/06/02 13:04
 **/
public class AsyncThreadExecutor {

    private ExecutorService executorService = Executors.newCachedThreadPool();

    public void fun() {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("执行业务逻辑...");
            }
        });
    }

    public static void main(String[] args) {
        AsyncThreadExecutor asyncThreadExecutor = new AsyncThreadExecutor();
        for (int i = 0; i < 10; i++) {
            asyncThreadExecutor.fun();
        }
    }
}
