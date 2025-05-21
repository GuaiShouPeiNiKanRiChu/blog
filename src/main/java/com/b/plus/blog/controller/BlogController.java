package com.b.plus.blog.controller;

import com.b.plus.blog.entity.Blog;
import com.b.plus.blog.mapper.BlogMapper;
import com.b.plus.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {
    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogService blogService;

    @PostMapping("/add")
    public void create(@RequestBody Blog blog) {
        blogService.getContent(blog.getTitle());
//        blogService.add(blog);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable Long id) {
        return blogMapper.deleteById(id);
    }

    @PutMapping
    public int update(@RequestBody Blog blog) {
        return blogMapper.update(blog);
    }

    @GetMapping("/{id}")
    public Blog getById(@PathVariable Long id) {
        return blogMapper.selectById(id);
    }

    @GetMapping
    public List<Blog> getAll() {
        return blogMapper.selectAll();
    }
}
