package com.ku.bobo.exception;

import com.ku.bobo.api.ICodeInfo;
import com.ku.bobo.exception.MainException;

/**
 * @Date 2020/11/2 15:18
 * @Created by xb
 */
public class AuthException extends MainException {

    public AuthException(ICodeInfo ICodeInfo) {
        super(ICodeInfo);
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }


}
