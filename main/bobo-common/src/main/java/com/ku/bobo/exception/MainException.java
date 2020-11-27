package com.ku.bobo.exception;

import com.ku.bobo.api.ICodeInfo;

/**
 * @Date 2020/4/30 9:35
 * @Created by xb
 */
public class MainException extends RuntimeException {

    private ICodeInfo ICodeInfo;

    public MainException( ICodeInfo ICodeInfo) {
        super( ICodeInfo.getMsg() );
        this.ICodeInfo = ICodeInfo;
    }

    public MainException( ) {
        super("服务器出现异常");
    }

    public MainException(String message) {
        super(message);
    }

    public MainException(Throwable cause) {
        super(cause);
    }

    public MainException(String message, Throwable cause) {
        super(message, cause);
    }

    public ICodeInfo getICodeInfo() {
        return ICodeInfo;
    }
}
