package org.basics.juc;

/**
 * T02_HowToCreateThread
 *
 * @author ljf
 * @version 3.0
 * @date 2023/06/05 13:28
 **/
public class T02_HowToCreateThread {

    public static void main(String[] args) {
        new MyThread().start();
        new Thread(new MyRun()).start();

        new Thread(() -> {
            System.out.println("Hello Lambda!");
        }).start();
    }

    private static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Hello MyThread!");
        }
    }

    private static class MyRun implements Runnable {
        @Override
        public void run() {
            System.out.println("Hello MyRun!");
        }
    }

    /**
     * 启动线程的三种方式  正常就两种
     * 1、Thread
     * 2、Runnable
     * 3、Executors.newCacheThread
     */
}
