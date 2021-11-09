package com.example.IdempotentDemo.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class WebControllerAdvice {

    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public ServerResponse serviceExceptionHandler(ServiceException ex) {
        return ServerResponse.error(ex.getMsg());
    }

//    @ResponseBody
//    @ExceptionHandler(Exception.class)
//    public ServerResponse exceptionHandler(Exception e) {
//        log.error("Exception: ", e);
//        return ServerResponse.error(ResponseCode.SERVER_ERROR.getMsg());
//    }

}
