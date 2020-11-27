package com.ku.bobo.modules.security.config;

import com.ku.bobo.api.CodeInfoEnum;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Date 2020/11/2 11:28
 * @Created by xb
 */
@Configuration
public class BasicAccessDeniedHandler implements AccessDeniedHandler {

    //当用户在没有授权的情况访问资源时，将调用该方法响应。
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, CodeInfoEnum.ACCESS_DENIED.getMsg());

    }
}
