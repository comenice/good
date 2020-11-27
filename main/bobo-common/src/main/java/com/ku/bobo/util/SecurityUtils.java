package com.ku.bobo.util;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ku.bobo.api.CodeInfoEnum;
import com.ku.bobo.exception.AuthException;
import com.ku.bobo.exception.MainException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * @Date 2020/11/4 11:36
 * @Created by xb
 */
public class SecurityUtils {

    /**
     * 获取当前登录的用户
     * @return UserDetails
     */
    public static UserDetails getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AuthException(CodeInfoEnum.UNAUTHORIZED);
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails;
//            UserDetailsService userDetailsService = SpringContextHolder.getBean(UserDetailsService.class);
//            return userDetailsService.loadUserByUsername(userDetails.getUsername());
        }
        throw new AuthException(CodeInfoEnum.UNAUTHORIZED);
    }

    /**
     * 获取当前登录的用户
     * @return UserDetails
     */
    public static Object getCurrentPrincipal() throws  AuthException{
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AuthException(CodeInfoEnum.UNAUTHORIZED);
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            return authentication.getPrincipal() ;
        }
        throw new AuthException(CodeInfoEnum.UNAUTHORIZED);
    }

    /**
     * 获取系统用户名称
     *
     * @return 系统用户名称
     */
    public static String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new MainException(CodeInfoEnum.UNAUTHORIZED);
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    /**
     * 获取系统用户ID
     * @return 系统用户ID
     */
    public static Long getCurrentUserId() {
        UserDetails userDetails = getCurrentUser();
        return new JSONObject(new JSONObject(userDetails)).get("id", Long.class);
    }

    /**
     * 获取当前用户的数据权限
     * @return /
     */
    public static List<Long> getCurrentUserDataScope(){
        UserDetails userDetails = getCurrentUser();
        JSONArray array = JSONUtil.parseArray(new JSONObject(userDetails).get("dataScopes"));
        return JSONUtil.toList(array,Long.class);
    }

}
