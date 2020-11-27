package com.ku.bobo.modules.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
/**
 * 获取当前用户登录信息
 * {@link com.ku.bobo.modules.security.service.dto.AuthUserDto}
 */
public @interface CurrentUser {

}
