package org.basics.juc;

/**
 * T03_Sleep_Yield_Join
 *
 * @author ljf
 * @version 3.0
 * @date 2023/06/05 13:32
 **/
public class T03_Sleep_Yield_Join {

    public static void main(String[] args) {
        //testSleep();
        //testYield();
        testJoin();
    }

    //睡眠
    static void testSleep() {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("A" + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //有事 退出一下   返回之后到等待队列排队
    static void testYield() {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("B" + i);
                if (i % 10 == 0) {
                    Thread.yield();
                }
            }

        }).start();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                System.out.println("C" + i);
                if (i % 10 == 0) {
                    Thread.yield();
                }
            }
        }).start();
    }

    //
    static void testJoin() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("D" + i);
                try {
                    Thread.sleep(500);
                    //TimeUnit.Milliseconds.sleep(500)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 10; i++) {
                System.out.println("E" + i);
                try {
                    Thread.sleep(500);
                    //TimeUnit.Milliseconds.sleep(500)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t3 = new Thread(() -> {
            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 10; i++) {
                System.out.println("F" + i);
                try {
                    Thread.sleep(500);
                    //TimeUnit.Milliseconds.sleep(500)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }
}
