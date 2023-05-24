package org.exercise;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * App
 *
 * @author ljf
 * @version 3.0
 * @date 2023/05/22 13:23
 **/
public class App {

    public static void main(String[] args) throws Exception {
        //zk是有session概念的，没有连接池的概念

        /**
         * watch 观察、回调
         * watch的注册只发生在读类型调用，比如get exites
         * 第一类：new zk的时候，传入的watch，这个watch，session接别的，跟path、node没有关系
         */
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ZooKeeper zk = new ZooKeeper(/*"172.28.27.153:2181,172.28.27.153:2181，172.28.27.153:2181，172.28.27.153:2181"*/ "172.28.27.153:2181", 3000,
                new Watcher() {
                    //watch的回调方法
                    @Override
                    public void process(WatchedEvent watchedEvent) {
                        Event.KeeperState state = watchedEvent.getState();
                        Event.EventType type = watchedEvent.getType();
                        String path = watchedEvent.getPath();
                        System.out.println(watchedEvent.toString());

                        switch (state) {
                            case Unknown:
                                break;
                            case Disconnected:
                                break;
                            case NoSyncConnected:
                                break;
                            case SyncConnected:
                                System.out.println("connected");
                                countDownLatch.countDown();
                                break;
                            case AuthFailed:
                                break;
                            case ConnectedReadOnly:
                                break;
                            case SaslAuthenticated:
                                break;
                            case Expired:
                                break;
                            case Closed:
                                break;
                        }

                        switch (type) {
                            case None:
                                break;
                            case NodeCreated:
                                break;
                            case NodeDeleted:
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
                });
        countDownLatch.await();

        ZooKeeper.States state = zk.getState();
        switch (state) {
            case CONNECTING:
                System.out.println("ing.....");
                break;
            case ASSOCIATING:
                break;
            case CONNECTED:
                System.out.println("ed....");
                break;
            case CONNECTEDREADONLY:
                break;
            case CLOSED:
                break;
            case AUTH_FAILED:
                break;
            case NOT_CONNECTED:
                break;
        }

        String pathName = zk.create("/ooxx", "olddata".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        Stat stat = new Stat();

        byte[] data = zk.getData("/ooxx", new Watcher() {

            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("getData watch:" + watchedEvent.toString());
                try {
                    //true default watch 被重新注册  new zk的watch
                    zk.getData("/ooxx", true, stat);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, stat);
        System.out.println(new String(data));

        //出发回调
        Stat stat1 = zk.setData("/ooxx", "newData".getBytes(), 0);
        Stat stat2 = zk.setData("/ooxx", "newData01".getBytes(), stat1.getVersion());

        System.out.println("-----------async start--------------");
        zk.getData("/ooxx", false, new AsyncCallback.DataCallback() {

            @Override
            public void processResult(int i, String s, Object o, byte[] bytes, Stat stat) {
                System.out.println("-----------async call back--------------");
                System.out.println(new String(bytes));
            }
        }, "abc");
        System.out.println("-----------async over--------------");

        Thread.sleep(2222222);
    }
}
