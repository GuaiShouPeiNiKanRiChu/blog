package com.b.plus.blog.entity;

import lombok.Data;

/**
 * @description: 分页参数
 * @author: biyunfei3@jd.com
 * @date: 2025-07-07 10:37
 **/
@Data
public class PaginatorDTO {

    private int limit;

    private int page;
}
