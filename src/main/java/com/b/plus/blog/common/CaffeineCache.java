package com.b.plus.blog.common;


import com.b.plus.blog.entity.Blog;
import com.b.plus.blog.mapper.BlogMapper;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @description: 本地缓存的使用
 * @author: biyunfei3@jd.com
 * @date: 2025-05-20 15:43
 **/
@Service
public class CaffeineCache {

    @Autowired
    private BlogMapper blogMapper;

    private final LoadingCache<String, String> cache =  Caffeine.newBuilder()
            .maximumSize(200)
            .refreshAfterWrite(5, TimeUnit.SECONDS)
            .build(this::getResultFromDb);

    public String get(String title) {
        return cache.get(title);
    }

    private String getResultFromDb(String title) {
        Blog blog = blogMapper.selectByTitle(title);
        System.out.println("getResultFromDb ===================== ");
        return blog.getContent();
    }
}
