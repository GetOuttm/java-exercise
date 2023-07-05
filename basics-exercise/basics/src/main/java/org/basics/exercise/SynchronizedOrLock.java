package org.basics.exercise;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * SynchronizedOrLock synchronized和Lock的区别
 *
 * @author ljf
 * @version 3.0
 * @date 2023/07/05 10:59
 **/
public class SynchronizedOrLock {

/*
特征	            synchronized	                 Lock
锁类型	            内置锁	                    显式锁
使用方式	            关键字	                    对象实例化
可重入	            是	                        是
可中断	            否	                        是
公平性	            不保证	                可手动设置为公平或非公平
锁的数量	            单一	                        可以创建多个
锁的灵活性	  限制较多，无法扩展或自定义功能	    提供更多灵活性，可以自定义功能
异常处理	      由JVM自动处理	                    需要手动处理
 */

    /**
     * synchronized是一种简单且易于使用的锁机制，适合简单的线程同步需求。而Lock接口提供了更多的功能和灵活性，适用于复杂的同步场景。所以，选择哪一种锁需要基于具体的需求和设计。
     */
}


/**
 * synchronized是Java的关键字，是基于JVM底层的Monitor锁实现的，用于修饰方法或代码块。在JDK1.6之前，
 * synchronized的锁性能较低，但是在JDK1.6开始，JDK对synchronized锁进行了大量的优化，
 * 同时引入了无锁、偏向锁、轻量级锁、重量级锁、锁消除、锁粗化等技术来提升synchronized锁的性能。
 * <p>
 * synchronized有两种使用方法
 * 1.锁代码块
 * 2.锁方法
 */
class SynchronizedExample {

    public static void main(String[] args) {
        SynchronizedExample synchronizedExample = new SynchronizedExample();

        //1.锁代码块
        synchronizedExample.syncCodeBlock();

        //2.锁方法
        synchronizedExample.syncMethod();
    }

    private synchronized void syncMethod() {
        System.out.println("sync method");
    }

    private void syncCodeBlock() {
        synchronized (this) {
            System.out.println("sync code block");
        }
    }
}

/**
 * Lock是Java并发包JUC下的接口，其有很多实现类，较为常用的是ReentrantLock，默认使用非公平锁。Lock需要进行手动加锁(lock)和解锁(unlock)。
 * 同时，Lock还有更强大的功能，如它的tryLock()方法可以非阻塞的去获取锁、使用Condition实现等待通知效果。
 */
class LockExample {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        lock.lock();
        try {
            System.out.println("lock.....");
        } finally {
            lock.unlock();
        }
    }
}
