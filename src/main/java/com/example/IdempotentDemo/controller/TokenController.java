package com.example.IdempotentDemo.controller;

import com.example.IdempotentDemo.common.ServerResponse;
import com.example.IdempotentDemo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping
    public ServerResponse token() {
        return ServerResponse.success(tokenService.getToken());
    }

}
