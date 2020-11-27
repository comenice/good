package com.ku.bobo.modules.system.service;

import com.ku.bobo.modules.system.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ku.bobo.modules.system.entity.SysUser;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxb
 * @since 2020-11-01
 */
public interface ISysRoleService extends IService<SysRole> {


    /**
     * 根据用户Id 获取用户权限信息
     * @param user 用户信息
     * @return 权限信息
     */
    List<GrantedAuthority> mapToGrantedAuthorities(SysUser user);




}
