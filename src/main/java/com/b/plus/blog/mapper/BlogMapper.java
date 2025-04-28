package com.b.plus.blog.mapper;

import com.b.plus.blog.entity.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogMapper {
    int insert(Blog blog);
    int deleteById(Long id);
    int update(Blog blog);
    Blog selectById(Long id);
    List<Blog> selectAll();
}
