package com.example.IdempotentDemo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ServiceException extends RuntimeException{

    private String code;
    private String msg;

    public ServiceException(String msg) {
        this.msg = msg;
    }


}
