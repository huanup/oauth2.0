package com.mine.sso.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : zhanghuan
 * @Date: 2021-11-14 14:31
 */
@RestController
public class TestContorller {

    @GetMapping("/test")
    public String test() {
        return "this is test request";
    }
}
