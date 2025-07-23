package com.b.plus.blog.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.b.plus.blog.entity.PaginatorDTO;
import com.b.plus.blog.entity.Student;

import java.util.List;
import java.util.Map;

/**
 * @description: 测试
 * @author: biyunfei3@jd.com
 * @date: 2025-07-07 10:38
 **/
public class BlogTest {

    public static void main(String[] args) {
        Map<String,String> resCk = JSON.parseObject(null, new TypeReference<Map<String,String>>() {
        });
        System.out.println(resCk);
    }
}
