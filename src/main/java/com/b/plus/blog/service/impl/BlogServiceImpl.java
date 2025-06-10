package com.b.plus.blog.service.impl;

import com.b.plus.blog.common.CaffeineCache;
import com.b.plus.blog.entity.Blog;
import com.b.plus.blog.mapper.BlogMapper;
import com.b.plus.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @description: 博客相关操作的实现类
 * @author: biyunfei3@jd.com
 * @date: 2025-05-10 20:03
 **/
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    @Qualifier("taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private CaffeineCache caffeineCache;

    @Override
    public void add(Blog blog) {
        blogMapper.insert(blog);
    }

    @Override
    public String getContent(String title) {
        long startTime = System.currentTimeMillis();
        String s = caffeineCache.get(title);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("getContent====="+ s);
        System.out.println("getContent方法执行耗时: " + duration + " 毫秒");
        return s;
    }

    public void insert(int i, Blog blog) throws InterruptedException {
        Thread.sleep(500);
        blog.setContent("=======" + i);
        blogMapper.insert(blog);
    }
}
