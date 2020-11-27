package com.ku.bobo.modules.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @Date 2020/11/4 10:27
 * @Created by xb
 * 自定义权限认证 @PreAuthorize("hasPermission('/role/**','role:add')")
 * 需要提供两个或以上参数,较繁琐,所以没有使用该方法.
 * 替代者 {@link ElPermissionConfig}
 */
//@Configuration
public class BasicPermissionEvaluator implements PermissionEvaluator {


    @Override
    public boolean hasPermission(Authentication authentication, Object o, Object o1) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
