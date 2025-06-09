package com.b.plus.blog.common;


import com.b.plus.blog.entity.Blog;
import com.b.plus.blog.mapper.BlogMapper;
import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @description: 本地缓存的使用
 * @author: biyunfei3@jd.com
 * @date: 2025-05-20 15:43
 **/
@Service
public class CaffeineCache {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired(required = false)
    private Executor cacheExecutor;

    private final LoadingCache<String, String> cache = Caffeine.newBuilder()
            .maximumSize(200)
            .expireAfterAccess(30, TimeUnit.SECONDS)
            .refreshAfterWrite(1, TimeUnit.MILLISECONDS)
            .build(this::getResultFromDb);

    private final AsyncLoadingCache<String, String> asyncCache = Caffeine.newBuilder()
            .maximumSize(200)
            .expireAfterAccess(30, TimeUnit.SECONDS)
            .refreshAfterWrite(1, TimeUnit.MILLISECONDS)
            .executor(task -> {
                if (cacheExecutor != null) {
                    cacheExecutor.execute(task);
                } else {
                    // 如果没有配置专用线程池，使用默认线程池
                    CompletableFuture.runAsync(task);
                }
            })
            .buildAsync(this::getResultFromDb);

    /**
     * 同步获取缓存值
     * @param title 缓存键
     * @return 缓存值
     */
    public String get(String title) {
        return cache.get(title);
    }

    /**
     * 异步获取缓存值，返回CompletableFuture
     * @param title 缓存键
     * @return 包含缓存值的CompletableFuture
     */
    public CompletableFuture<String> getAsync(String title) {
        return asyncCache.get(title);
    }
    
    /**
     * 异步获取缓存值，通过回调函数处理结果
     * @param title 缓存键
     * @param callback 处理结果的回调函数
     */
    public void getAsyncWithCallback(String title, Consumer<String> callback) {
        asyncCache.get(title).thenAccept(callback);
    }
    
    /**
     * 异步获取缓存值，带有异常处理的回调
     * @param title 缓存键
     * @param successCallback 成功时的回调函数
     * @param errorCallback 失败时的回调函数
     */
    public void getAsyncWithErrorHandling(String title, Consumer<String> successCallback, Consumer<Throwable> errorCallback) {
        asyncCache.get(title)
                .thenAccept(successCallback)
                .exceptionally(ex -> {
                    errorCallback.accept(ex);
                    return null;
                });
    }

    /**
     * 阻塞等待异步结果（不推荐使用，仅用于兼容旧代码）
     * @param title 缓存键
     * @return 缓存值
     * @throws ExecutionException 执行异常
     * @throws InterruptedException 中断异常
     * @deprecated 推荐使用 {@link #getAsync(String)} 或 {@link #getAsyncWithCallback(String, Consumer)}
     */
    @Deprecated
    public String getAsyncBlocking(String title) throws ExecutionException, InterruptedException {
        return asyncCache.get(title).get();
    }

    /**
     * 从数据库获取结果
     * @param title 查询条件
     * @return 查询结果
     */
    private String getResultFromDb(String title) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {

        }
        Blog blog = blogMapper.selectByTitle(title);
        return blog != null ? blog.getContent() : null;
    }
}
