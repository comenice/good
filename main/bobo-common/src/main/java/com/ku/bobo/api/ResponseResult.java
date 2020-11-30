package com.ku.bobo.api;

import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * @Date 2020/11/23 10:05
 * @Created by xb
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
/**
 * 标注controller 类或方法返回值是否统一处理
 * 处理 {@link CommResponseBodyAdvice}
 */
public @interface ResponseResult {

}
