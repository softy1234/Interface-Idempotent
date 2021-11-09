package com.example.IdempotentDemo.service.impl;

import com.example.IdempotentDemo.cache.TimedLocalCache;
import com.example.IdempotentDemo.cache.TimedLocalCachedDataDTO;
import com.example.IdempotentDemo.common.ResponseCode;
import com.example.IdempotentDemo.common.ServiceException;
import com.example.IdempotentDemo.service.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenServiceImpl implements TokenService {
//    private ConcurrentHashMap<String,Object> cache = new ConcurrentHashMap<>();
    // 初始化内部缓存，并指定超时时间
    private static final TimedLocalCache<String, String> cache = new TimedLocalCache<>(10);

    private static final String TOKEN_NAME = "token";

    @Override
    public String getToken() {
        //通过UUID来生成token
        String tokenValue = "idempotent:token:" + UUID.randomUUID().toString();
        //将token放入cache中
        cache.putCache(tokenValue,"0");
        return tokenValue;
    }

    @Override
    public void checkToken(HttpServletRequest request) throws Exception {
        String token = request.getHeader(TOKEN_NAME);
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(TOKEN_NAME);
            if (StringUtils.isEmpty(token)) {
                //没有携带token，抛异常，这里的异常需要全局捕获
                throw new ServiceException(ResponseCode.ILLEGAL_ARGUMENT.getMsg());
            }
        }
        //token不存在，说明token已经被其他请求删除或者是非法的token
        if (cache.getCache(token) == null) {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }
        Object del = cache.remove(token);
        if (del == null) {
            //token删除失败，说明token已经被其他请求删除
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }
    }

}
