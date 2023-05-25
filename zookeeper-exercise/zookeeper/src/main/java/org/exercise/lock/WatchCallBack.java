package org.exercise.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.exercise.config.MyConf;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * WatchCallBack
 *
 * @author ljf
 * @version 3.0
 * @date 2023/05/24 13:38
 **/
public class WatchCallBack implements Watcher, AsyncCallback.StringCallback, AsyncCallback.Children2Callback, AsyncCallback.StatCallback {

    ZooKeeper zooKeeper;

    String threadName;

    String pathName;

    CountDownLatch countDownLatch = new CountDownLatch(1);

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    public void tryLock() {
        try {
            //创建有序的临时节点
            System.out.println(threadName + "create ....");
            // if (zooKeeper.getData("/"))
            zooKeeper.create("/lock", threadName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, this, "ctx");
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unLock() {
        try {
            zooKeeper.delete(pathName,-1);
            System.out.println(threadName + " over work....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        //如果第一个哥们，那个锁释放了，其实只有第二个收到了回调事件！！
        //如果，不是第一个哥们，某一个，挂了，也能造成他后边的收到这个通知，从而让他后边那个跟去watch挂掉这个哥们前边的。。。
        switch (watchedEvent.getType()) {
            case None:
                break;
            case NodeCreated:
                break;
            case NodeDeleted:
                zooKeeper.getChildren("/", false, this, "sdf");
                break;
            case NodeDataChanged:
                break;
            case NodeChildrenChanged:
                break;
            case DataWatchRemoved:
                break;
            case ChildWatchRemoved:
                break;
            case PersistentWatchRemoved:
                break;
        }
    }

    @Override
    public void processResult(int i, String s, Object o, String s1) {
        if (s1 != null) {
            System.out.println(threadName + " create node :" + s1);
            pathName = s1;
            zooKeeper.getChildren("/", false, this, "sdf");
        }
    }

    // getChildren call back
    @Override
    public void processResult(int i, String s, Object o, List<String> list, Stat stat) {
        //一定能看到自己前面的。。。。
        // System.out.println(threadName + "lock locks....");
        // for (String child : list) {
        //     System.out.println(child);
        // }

        //排序
        Collections.sort(list);
        int i1 = list.indexOf(pathName.substring(1));
        //是不是第一个
        if (i1 == 0) {
            // 是的话就countDown
            System.out.println(threadName + " i am first ...");
            try {
                zooKeeper.setData("/" ,threadName.getBytes(),-1);
                countDownLatch.countDown();
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            //不是
            zooKeeper.exists("/"+list.get(i1 -1), this,this,"sdf");
        }
    }

    @Override
    public void processResult(int i, String s, Object o, Stat stat) {

    }
}
