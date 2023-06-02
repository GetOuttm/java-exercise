package org.basics.thread.async.a_4;

import jdk.jfr.Enabled;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * TaskPoolConfig  spring的@Async异步
 *
 * @author ljf
 * @version 3.0
 * @date 2023/06/02 13:26
 **/
@EnableAsync //启用异步功能
@Configuration
public class TaskPoolConfig {

    /**
     * 自定义线程池
     */
    @Bean("taskExecutor")
    public Executor tackExecutor() {
        //返回可用处理器的java虚拟机的数量 12
        int i = Runtime.getRuntime().availableProcessors();
        System.out.println("系统最大线程数：" +i);
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池大小
        executor.setCorePoolSize(16);
        //最达线程数
        executor.setMaxPoolSize(20);
        //配置队列容量，默认值为Integer.MAX_VALUE
        executor.setQueueCapacity(999999);
        //活跃时间
        executor.setKeepAliveSeconds(60);
        //线程名字前缀
        executor.setThreadNamePrefix("asyncServiceExecutor - ");
        //设置次执行程序应该在关闭时阻止的最达秒数，以便在容器的其余部分继续关闭之前等待剩余的任务完成他们的执行
        executor.setAwaitTerminationMillis(60);
        //等待所有的任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
}
