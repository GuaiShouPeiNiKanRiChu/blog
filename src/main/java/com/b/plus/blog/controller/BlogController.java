package com.b.plus.blog.controller;

import com.b.plus.blog.entity.Blog;
import com.b.plus.blog.mapper.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {
    @Autowired
    private BlogMapper blogMapper;

    @PostMapping
    public int create(@RequestBody Blog blog) {
        return blogMapper.insert(blog);
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
