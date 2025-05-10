package com.b.plus.blog.service;

import com.b.plus.blog.entity.Blog;
import com.b.plus.blog.mapper.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 博客服务类
 * 演示如何在service层使用线程池
 */
@Service
public class BlogService {

    private final BlogMapper blogMapper;
    private final ThreadPoolService threadPoolService;

    @Autowired
    public BlogService(BlogMapper blogMapper, ThreadPoolService threadPoolService) {
        this.blogMapper = blogMapper;
        this.threadPoolService = threadPoolService;
    }

    /**
     * 异步获取所有博客
     * @return CompletableFuture<List<Blog>>
     */
    public CompletableFuture<List<Blog>> findAllBlogsAsync() {
        return threadPoolService.executeAsync(blogMapper::selectAll);
    }

    /**
     * 异步获取博客详情
     * @param id 博客ID
     * @return CompletableFuture<Blog>
     */
    public CompletableFuture<Blog> findBlogByIdAsync(Long id) {
        return threadPoolService.executeAsync(() -> blogMapper.selectById(id));
    }

    /**
     * 异步保存博客
     * @param blog 博客实体
     * @return CompletableFuture<Integer>
     */
    public CompletableFuture<Integer> saveBlogAsync(Blog blog) {
        return threadPoolService.executeAsync(() -> blogMapper.insert(blog));
    }

    /**
     * 异步更新博客
     * @param blog 博客实体
     * @return CompletableFuture<Integer>
     */
    public CompletableFuture<Integer> updateBlogAsync(Blog blog) {
        return threadPoolService.executeAsync(() -> blogMapper.update(blog));
    }

    /**
     * 异步删除博客
     * @param id 博客ID
     * @return CompletableFuture<Integer>
     */
    public CompletableFuture<Integer> deleteBlogAsync(Long id) {
        return threadPoolService.executeAsync(() -> blogMapper.deleteById(id));
    }

    /**
     * 同步获取所有博客（普通方法，不使用线程池）
     * @return List<Blog>
     */
    public List<Blog> findAllBlogs() {
        return blogMapper.selectAll();
    }

    /**
     * 同步获取博客详情（普通方法，不使用线程池）
     * @param id 博客ID
     * @return Blog
     */
    public Blog findBlogById(Long id) {
        return blogMapper.selectById(id);
    }
}
