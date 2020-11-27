package com.ku.bobo.modules.system.controller;

import com.ku.bobo.api.CommonResult;
import com.ku.bobo.exception.AuthException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Date 2020/11/1 14:30
 * @Created by xb
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/t1")
    @ResponseBody
    @PreAuthorize("hasAuthority('p:get')")
    public String testAuth1(){
        return "调用成功";
    }


    @GetMapping("/t2")
    public CommonResult testAuth2(){
        if ( true ){
            throw new AuthException( "自定义异常" );
        }
        return CommonResult.success("成功");
    }

}
