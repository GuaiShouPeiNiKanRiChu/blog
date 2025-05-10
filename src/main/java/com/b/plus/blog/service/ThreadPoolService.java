package com.b.plus.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

/**
 * 线程池服务类
 * 提供异步执行任务的方法
 */
@Service
public class ThreadPoolService {

    private final Executor taskExecutor;

    @Autowired
    public ThreadPoolService(@Qualifier("taskExecutor") Executor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    /**
     * 执行异步任务
     * @param task 任务
     * @param <T> 返回值类型
     * @return CompletableFuture
     */
    public <T> CompletableFuture<T> executeAsync(Supplier<T> task) {
        return CompletableFuture.supplyAsync(task, taskExecutor);
    }

    /**
     * 执行异步任务（无返回值）
     * @param runnable 任务
     * @return CompletableFuture<Void>
     */
    public CompletableFuture<Void> executeAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable, taskExecutor);
    }
}
