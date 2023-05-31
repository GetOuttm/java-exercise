package org.basics.thread;

import javax.sound.midi.Soundbank;
import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatchTest CountDownLatch
 *
 * @author ljf
 * @version 3.0
 * @date 2023/05/31 15:31
 **/
public class CountDownLatchTest {

    /**
     * CountDownLatch 能够使一个线程在等待另外一些线程完成各自工作之后，再继续执行。它相当于是一个计数器，这个计数器的初始值就是线程的数量，
     * 每当一个任务完成后，计数器的值就会减一，当计数器的值为 0 时，表示所有的线程都已经任务了，然后在 CountDownLatch 上等待的线程就可以恢复执行接下来的任务。
     */
    /**
     * 典型的应用场景就是当一个服务启动时，同时会加载很多组件和服务，这时候主线程会等待组件和服务的加载。
     * 当所有的组件和服务都加载完毕后，主线程和其他线程在一起完成某个任务
     */

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        Increment increment = new Increment(countDownLatch);
        Decrement decrement = new Decrement(countDownLatch);

        new Thread(increment).start();
        new Thread(decrement).start();

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Decrement implements Runnable {

    CountDownLatch countDownLatch;

    public Decrement(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            for (long i = countDownLatch.getCount(); i > 0; i--) {
                Thread.sleep(1000);
                System.out.println("countDownLatch");
                this.countDownLatch.countDown();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Increment implements Runnable {

    CountDownLatch countDownLatch;

    public Increment(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            System.out.println("await");
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Waiter Released");
    }
}