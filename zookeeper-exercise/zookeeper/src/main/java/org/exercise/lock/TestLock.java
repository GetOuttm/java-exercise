package org.exercise.lock;

import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @NAME: TestLock
 * @USER: ljf
 * @TIME: 2023/5/25  21:57
 * @REMARK:
 **/
public class TestLock {

    ZooKeeper zooKeeper;

    @Before
    public void conn() {
        zooKeeper = ZKUtils.getZookeeper();
    }

    @After
    public void close() {
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void lock() {
        for (int i = 0; i < 10; i++) {
            new Thread() {
                @Override
                public void run() {
                    WatchCallBack watchCallBack = new WatchCallBack();
                    watchCallBack.setZooKeeper(zooKeeper);
                    String threadName = Thread.currentThread().getName();
                    watchCallBack.setThreadName(threadName);
                    // 开启10个线程
                    // 每个线程
                    // 抢锁
                    watchCallBack.tryLock();
                    // 干活
                    System.out.println(threadName + "干活。。。。");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 释放锁
                    watchCallBack.unLock();
                }
            }.start();
        }


        while (true) {

        }
    }
}