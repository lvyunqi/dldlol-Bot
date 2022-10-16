package com.mryunqi.qimenbot.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync //开启异步
public class TaskConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        // 设置线程数
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //核心线程数
        taskExecutor.setCorePoolSize(25);
        //最大线程数
        taskExecutor.setMaxPoolSize(50);
        //阻塞队列
        taskExecutor.setQueueCapacity(100);
        //初始化线程
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
}

