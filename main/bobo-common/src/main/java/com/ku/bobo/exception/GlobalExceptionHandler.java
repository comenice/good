package com.ku.bobo.exception;

import com.ku.bobo.api.CommonResult;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 * @Date 2020/4/30 9:39
 * @Created by xb
 */
@ControllerAdvice
@Slf4j
@Order(1)
public class GlobalExceptionHandler {

    @ExceptionHandler( MainException.class )
    @ResponseBody
    public CommonResult handle(MainException e ){
        if ( e.getICodeInfo()!=null ){
            //自定义返回信息包括msg和code
            return CommonResult.failed( e.getICodeInfo() );
        }
//        log.error(e.getMessage());
        return CommonResult.failed( e.getMessage() );
    }

}

