package org.basics.juc.juc.c_000;

public class T04_ThreadState {

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println(this.getState());

            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
        }
    }

    public static void main(String[] args) {
        Thread t = new MyThread();

        System.out.println(t.getState());

        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(t.getState());
    }

    //NEW - RUNABLE(Ready-Running) - Teminated
    //        TimedWaiting
    //        Waiting
    //        Blocked

    // 调用两次start()方法的后果
// 在Java中，线程的start()方法只能被调用一次，如果第二次调用，就会抛出运行时异常；
// 因为多次调用start（）方法，会被认为是编程错误。
// 在Java中，线程的运行状态被定义为6个枚举值，分别是：
// 1新建状态（NEW）：线程已经建好，还没调用start()方法。
// 2.就绪状态（RUNNABLE）:线程可能正在运行，也可能在就绪队列等待系统分配CPU资源。
// 3.阻塞状态（BLOCKED）:线程处于等待（Monitor Lock）锁的状态。
// 4.等待状态（WAITTING）:线程处于条件等待状态，当触发后，会被唤醒。
// 5.计时等待状态（TIMED_WAIT）：与等待状态时一样的，只是比等待状态多了一个超时触发机制。
// 6.终止状态（TERMINATED）：表示线程执行结束。
// 第一次调用start()方法时，线程可能处于终止或这是其他的非NEW状态；这时，当我们再次调用start()方法时，相当于让这个正在运行的线程重新运行一遍，这样无论是从线程安全的角度来看还是从线程本身的执行逻辑来看，都是不合理的。
// 因此，为了避免这个问题的出现，Java会去判断当前线程的运行状态。
}
