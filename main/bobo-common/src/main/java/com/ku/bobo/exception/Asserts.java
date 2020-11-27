package com.ku.bobo.exception;

import com.ku.bobo.api.ICodeInfo;
import com.ku.bobo.constant.CommConstant;
import lombok.extern.log4j.Log4j2;

/**
 * @Date 2020/4/30 9:44
 * @Created by xb
 */
@Log4j2
public class Asserts {
    /**
     *
     * @param msg 异常信息 日志记录
     * @param isDefMsg 是否使用默认的异常反馈
     * isDefMsg的作用主要用于，有一些异常信息不够用好、不希望用户看见，则使用默认的。
     */
    public static void fail( String msg , Boolean isDefMsg ){
        //存入数据库
        log.error( msg );
        if ( isDefMsg ){
           throw new MainException( CommConstant.DEFAULT_ERROR_MSG );
        }
        throw new MainException( msg );
    }

    public static void fail( ICodeInfo ICodeInfo){
        throw new MainException(ICodeInfo);
    }
}
