package org.basics.thread.async.a_8;

import com.google.common.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * GuavaThread
 *
 * @author ljf
 * @version 3.0
 * @date 2023/06/02 14:03
 **/
@Slf4j
public class GuavaThread {

    public void listenableFutureTest() throws InterruptedException, ExecutionException {
        System.out.println(printThread("小明点餐"));
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        ListenableFuture<String> listenableFuture = listeningExecutorService.submit(() -> {
            System.out.println(printThread("厨师开始炒菜"));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(printThread("厨师炒好菜"));
            return "饭菜好了";
        });
        Futures.addCallback(listenableFuture, new FutureCallback<String>() {
            @Overridepublic
            void onSuccess(@Nullable String s) {
                System.out.println(printThread(s + ",小明开始吃饭"));
            }

            @Overridepublic
            void onFailure(Throwable throwable) {
                System.out.println(printThread(throwable.getMessage()));
            }
        }, executorService);
        System.out.println(printThread("小明开始玩游戏"));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(printThread("小明结束玩游戏"));
        listenableFuture.get();
        listeningExecutorService.shutdown();
        executorService.shutdown();
    }

    public static void main(String[] args) {
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        final ListenableFuture<Integer> listenableFuture = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.info("callable execute...");
                TimeUnit.SECONDS.sleep(1);
                return 1;
            }
        });

        Futures.addCallback(listenableFuture, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                //成功执行...
                System.out.println("Get listenable future's result with callback " + result);
            }

            @Override
            public void onFailure(Throwable t) {
                //异常情况处理...
                t.printStackTrace();
            }
        });
    }
}
