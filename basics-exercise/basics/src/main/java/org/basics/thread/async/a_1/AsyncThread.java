package org.basics.thread.async.a_1;

/**
 * AsyncThread 异步线程
 *
 * @author ljf
 * @version 3.0
 * @date 2023/06/02 13:00
 **/
public class AsyncThread extends Thread {

    @Override
    public void run() {
        System.out.println("Current thread name: " + Thread.currentThread().getName());
    }

    /**
     * 当然如果每次都创建一个Thread线程，频繁的创建、销毁，浪费系统资源
     * 优化--AsyncThreadExecutor
     */
    public static void main(String[] args) {
        AsyncThread asyncThread = new AsyncThread();
        asyncThread.run();
    }
}
