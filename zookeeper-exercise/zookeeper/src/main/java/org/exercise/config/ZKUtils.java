package org.exercise.config;

import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * ZKUtils
 *
 * @author ljf
 * @version 3.0
 * @date 2023/05/24 13:24
 **/
public class ZKUtils {

    private static ZooKeeper zookeeper;

    private static String address = "172.28.27.153:2181/testConf";

    private static DefaultWatch watch = new DefaultWatch();

    private static CountDownLatch init = new CountDownLatch(1);

    public static ZooKeeper getZookeeper() {

        try {
            zookeeper = new ZooKeeper(address, 1000, watch);
            watch.setCountDownLatch(init);
            init.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return zookeeper;
    }
}
