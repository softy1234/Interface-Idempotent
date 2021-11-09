package com.example.IdempotentDemo.controller;

import com.example.IdempotentDemo.annotation.ApiIdempotent;
import com.example.IdempotentDemo.common.ServerResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    @GetMapping("/test/test1")
    @ApiIdempotent
    public ServerResponse test() {
        return ServerResponse.success("success");
    }

    @GetMapping("/notest")
    public ServerResponse notest() {
        return ServerResponse.success("success");
    }
}
