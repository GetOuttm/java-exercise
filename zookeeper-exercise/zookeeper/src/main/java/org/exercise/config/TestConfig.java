package org.exercise.config;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
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

        zooKeeper.exists("/AppConf", watchCallBack.watch,"ABC");
    }
}
