package com.ku.bobo.modules.security.service;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ku.bobo.constant.HintInfoConstant;
import com.ku.bobo.exception.AuthException;
import com.ku.bobo.modules.security.service.dto.AuthUserDto;
import com.ku.bobo.modules.system.entity.SysUser;
import com.ku.bobo.modules.system.mapper.SysUserMapper;
import com.ku.bobo.modules.system.service.ISysRoleService;
import com.ku.bobo.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

/**
 * @Date 2020/10/30 11:04
 * @Created by xb
 */
@Service("udService")
public class UserDetailsServiceImpl implements UserDetailsService {


    @PostConstruct
    public void init(){
        System.out.println( "init success" );
    }

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty( username )){
            throw new AuthException(HintInfoConstant.User.NULL);
        }
        LambdaQueryWrapper<SysUser> userQuery = new LambdaQueryWrapper();
        userQuery.eq( SysUser::getUsername , username);
        SysUser sysUser = sysUserService.getOne( userQuery );
        if ( ObjectUtil.isNull( sysUser )  ){
            throw new AuthException(HintInfoConstant.User.ERROR);
        }
        if ( sysUser.getStatus() == 0  ){
            throw new AuthException(HintInfoConstant.User.DISABLED);
        }
        AuthUserDto authUserDto = new AuthUserDto();
        authUserDto.setId( sysUser.getId() );
        authUserDto.setUsername( sysUser.getUsername() );
        authUserDto.setPassword( sysUser.getPassword() );
        authUserDto.setStatus( sysUser.getStatus() );
        authUserDto.setAuthorityList(  sysRoleService.mapToGrantedAuthorities(sysUser) );
        return authUserDto;
    }
}
