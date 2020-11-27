package com.ku.bobo.modules.security.config;

import com.ku.bobo.util.SecurityUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Date 2020/11/4 11:35
 * @Created by xb
 * 自定义权限认证
 * 代替了原有的权限认证方式 {@link BasicPermissionEvaluator}
 *
 * 使用: @PreAuthorize("@el.check('xxx')")
 */
@Configuration(value = "el")
public class ElPermissionConfig {

    public Boolean check(String ...permissions){
        // 获取当前用户的所有权限
        List<String> elPermissions = SecurityUtils.getCurrentUser().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        // 判断当前用户的所有权限是否包含接口上定义的权限
        return elPermissions.contains("admin") || elPermissions.containsAll(Arrays.asList(permissions));
    }
}
