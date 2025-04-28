package com.b.plus.blog.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // 根据ID获取用户
    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id) {
        return "Hello World";
    }


}
