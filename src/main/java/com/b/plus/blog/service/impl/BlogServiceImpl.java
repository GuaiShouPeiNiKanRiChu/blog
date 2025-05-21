package com.b.plus.blog.service.impl;

import com.b.plus.blog.common.CaffeineCache;
import com.b.plus.blog.entity.Blog;
import com.b.plus.blog.mapper.BlogMapper;
import com.b.plus.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

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
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            try {
                int finalI = i;
                taskExecutor.execute(()-> {
                    try {
                        insert(finalI, blog);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        long end = System.currentTimeMillis();
        long durationMillis = end - start;
        double durationSeconds = durationMillis / 1000.0;
        System.out.println("方法耗时: " + durationSeconds + " 秒");
    }

    @Override
    public String getContent(String title) {
        String s = caffeineCache.get(title);
        return s;
    }

    public void insert(int i, Blog blog) throws InterruptedException {
        Thread.sleep(500);
        blog.setContent("=======" + i);
        blogMapper.insert(blog);
    }
}
