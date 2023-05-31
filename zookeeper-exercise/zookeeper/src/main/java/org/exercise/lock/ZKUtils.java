package org.exercise.lock;

import org.apache.zookeeper.ZooKeeper;
import org.exercise.config.DefaultWatch;

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

    //节点需要人工创建  create /testLock ""   创建子节点然后删除测试
    private static String address = "172.28.27.153:2181/testLock";

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
