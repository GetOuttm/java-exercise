package org.basics.juc;

import org.springframework.aop.ThrowsAdvice;

/**
 * T04_ThreadState
 *
 * @author ljf
 * @version 3.0
 * @date 2023/06/05 13:47
 **/
public class T04_ThreadState {

    public static void main(String[] args) {
        Thread thread = new MyThread();

        System.out.println(thread.getState());

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(thread.getState());
    }

    private static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println(this.getState());
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
        }
    }

    /**
     * 线程状态
     * 1、NEW
     * 2、Runnable
     *      Ready
     *      Running
     * 3、Terminated
     * 4、TimeWaiting
     * 5、Waiting
     * 6、Blocked
     */
}
