package com.example.IdempotentDemo.service;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {
    String getToken();

    void checkToken(HttpServletRequest request) throws Exception;
}
