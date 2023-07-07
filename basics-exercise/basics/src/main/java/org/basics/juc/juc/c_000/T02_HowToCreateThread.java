package org.basics.juc.juc.c_000;

public class T02_HowToCreateThread {

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Hello MyThread!");
        }
    }

    static class MyRun implements Runnable {
        @Override
        public void run() {
            System.out.println("Hello MyRun!");
        }
    }

    public static void main(String[] args) {
        new MyThread().start();
        new Thread(new MyRun()).start();
        new Thread(() -> {
            System.out.println("Hello Lambda!");
        }).start();
    }
}

//请你告诉我启动线程的三种方式 1：Thread 2: Runnable 3:Executors.newCachedThread(通过线程池去地洞)

//1.run()和start()的区别
// 1.start()方法是Java线程约定的内置方法，能够保证代码在新的线程上下文中运行。
// 2.start()方法包含了创建新线程的特殊代码逻辑。而run()方法一般是我们自己写的代码逻辑，很显然没这个功能。

// 2.为什么启动一个线程不直接调用run(),而要调用start()启动?
// 1.如果直接调用run()方法，那么它就只是作为一个普通方法的调用，程序中依然只有一个主线程，并且只能顺序执行，需要等待run()方法执行结束后才能继续执行后面的代码。
// 2.创建线程的目的是为了更充分地利用CPU里面的资源，如果直接调用run()方法,就失去了创建线程的意义。

// 4.如何安全地中断一个正在运行中的线程？
// 线程是操作系统进行运算调度的最小单位，线程是系统级别的概念。在Java中实现的线程最终的调度都是由操作系统来决定的，JVM只是对操作系统层面的线程做了一层包装。
// 在JAVA里面调用Thread的start方法，来启动一个线程的时候，只是去告诉操作系统这个线程可以执行，但最终的执行是交给CPU进行执行的，由操作系统的调度算法来决定。
// 在JAVA的Thread 的API里提供了一个stop方法，可以去强行中止线程，但不安全；线程的任务有可能还没完成，突然中断会导致运行结果不正确的问题。
// 想要安全地区中断一个线程，只能在线程内部去埋一个“钩子”，外部程序通过钩子来触发对线程的中断的命令；因此，在JAVA 的Thread中，提供了一个叫做interrupt()的方法，这个方法配合isInterrupted()方法来使用，可以去实现线程的安全中断。
