package org.basics.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * StudentRunRace 学生赛跑
 *
 * @author ljf
 * @version 3.0
 * @date 2023/05/31 15:42
 **/
public class StudentRunRace {

    //https://www.cnblogs.com/cxuanBlog/p/14166322.html

    CountDownLatch stopLatch = new CountDownLatch(1);
    CountDownLatch runLatch = new CountDownLatch(10);

    public void waitSignal() throws Exception {
        System.out.println("选手" + Thread.currentThread().getName() + "正在等待裁判发布口令");
        stopLatch.await();
        System.out.println("选手" + Thread.currentThread().getName() + "已接受裁判口令");
        Thread.sleep((long) (Math.random() * 10000));
        System.out.println("选手" + Thread.currentThread().getName() + "到达终点");
        runLatch.countDown();
    }

    public void waitStop() throws Exception {
        Thread.sleep((long) (Math.random() * 10000));
        System.out.println("裁判" + Thread.currentThread().getName() + "即将发布口令");
        stopLatch.countDown();
        System.out.println("裁判" + Thread.currentThread().getName() + "已发送口令，正在等待所有选手到达终点");
        runLatch.await();
        System.out.println("所有选手都到达终点");
        System.out.println("裁判" + Thread.currentThread().getName() + "汇总成绩排名");
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        StudentRunRace studentRunRace = new StudentRunRace();
        for (int i = 0; i < 10; i++) {
            Runnable runnable = () -> {
                try {
                    studentRunRace.waitSignal();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            executorService.execute(runnable);
        }
        try {
            studentRunRace.waitStop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}
