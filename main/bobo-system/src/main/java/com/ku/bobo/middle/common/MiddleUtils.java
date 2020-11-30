package com.ku.bobo.middle.common;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.druid.util.StringUtils;
import com.ku.bobo.api.CodeInfoEnum;
import com.ku.bobo.modules.security.config.AuthProperties;
import com.ku.bobo.modules.security.config.JwtAuthenticationFilter;
import com.ku.bobo.exception.AuthException;
import com.ku.bobo.modules.security.service.dto.AuthUserDto;
import com.ku.bobo.modules.security.util.JwtUtil;
import com.ku.bobo.modules.system.entity.SysUser;
import com.ku.bobo.util.ApplicationUtils;
import com.ku.bobo.util.SecurityUtils;
import com.ku.bobo.util.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Date 2020/11/24 10:15
 * @Created by xb
 * 本准备写在comm模块，但需要依赖system模块下的类，会出现循环依赖。
 *
 * 该类就是一个工具类
 */
@Slf4j
public class MiddleUtils {

    /**
     * 获取SysUser用户
     * @param security 信息是否安全
     * @param unknownLogin 设为true可在未认证的接口获取用户信息。详{@link AuthProperties#weakIgnores}
     * @return
     */
    public static SysUser getCurrentSysUser(  boolean security , boolean unknownLogin ){
        Object principal = null;
        try {
            principal = SecurityUtils.getCurrentPrincipal();
        } catch (AuthException e) {

            if ( ObjectUtil.equal( e.getICodeInfo() , CodeInfoEnum.UNAUTHORIZED ) ){
                if ( unknownLogin ){
                    JwtUtil jwtUtil = ApplicationUtils.getBean(JwtUtil.class);
                    String token = jwtUtil.getJwtFromRequest(WebUtils.getRequest());
                    if (StringUtils.isEmpty( token )){
                        return null;
                    }else {
                        principal = jwtUtil.getAuthentication(WebUtils.getRequest()).getPrincipal();
                    }
                }
            } else{
                return null;
            }
        }

        SysUser sysUser = new SysUser();
        /**
         * 存入上下文中的是AuthUserDto
         * {@link JwtAuthenticationFilter#doFilterInternal}
         */
        if ( principal instanceof UserDetails){
            AuthUserDto authUser = (AuthUserDto) principal;
            sysUser.setId( authUser.getId() );
            sysUser.setUsername( authUser.getUsername() );
            if ( !security ){
               sysUser.setPassword( authUser.getPassword() );
            }
        }
        return sysUser;
    }

    public static SysUser getCurrentSysUser(  boolean security ){
        return getCurrentSysUser( security , false );
    }


}
