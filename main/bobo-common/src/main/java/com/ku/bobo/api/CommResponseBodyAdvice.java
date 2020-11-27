package com.ku.bobo.api;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Arrays;

/**
 * @Date 2020/11/23 9:39
 * @Created by xb
 */
@RestControllerAdvice
public class CommResponseBodyAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        ResponseResult annotation = methodParameter.
                getContainingClass().getAnnotation(ResponseResult.class);
        if ( annotation != null ){
            return true;
        }
        if ( methodParameter.getMethodAnnotation( ResponseResult.class ) != null ){
            return true;
        }
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        System.out.println("body = " + body);
        if ( body instanceof CommonResult ){
            return body;
        }

        if ( null == body ){

        }
        return CommonResult.success( body );
    }
}
