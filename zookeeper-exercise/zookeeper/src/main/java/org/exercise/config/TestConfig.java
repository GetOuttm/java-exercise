package org.exercise.config;

import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * TestConfig
 *
 * @author ljf
 * @version 3.0
 * @date 2023/05/24 13:24
 **/
public class TestConfig {

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
    public void getConf() {
        WatchCallBack watchCallBack = new WatchCallBack();
        watchCallBack.setZooKeeper(zooKeeper);
        MyConf myConf = new MyConf();
        watchCallBack.setMyConf(myConf);

        watchCallBack.aWait();
        //可能性：
        //1、节点不存在
        //2、节点存在

        while (true) {
            if (myConf.getConf().equals("")) {
                System.out.println("conf 被删除 。。。。");
                watchCallBack.aWait();
            } else {
                System.out.println(myConf.getConf());
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
