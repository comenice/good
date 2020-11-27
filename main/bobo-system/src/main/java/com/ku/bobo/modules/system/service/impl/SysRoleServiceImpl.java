package com.ku.bobo.modules.system.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ku.bobo.modules.system.entity.SysPermission;
import com.ku.bobo.modules.system.entity.SysRole;
import com.ku.bobo.modules.system.entity.SysUser;
import com.ku.bobo.modules.system.mapper.SysPermissionMapper;
import com.ku.bobo.modules.system.mapper.SysRoleMapper;
import com.ku.bobo.modules.system.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxb
 * @since 2020-11-01
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysPermissionMapper permissionMapper;

    @Override
    public List<GrantedAuthority> mapToGrantedAuthorities(SysUser user) {

        //获取该用户所有角色
        Set<SysRole> roles = roleMapper.findByUserId(user.getId());
        if ( roles.isEmpty() ){
            return null;
        }

        //组装查询sql,获取角色所有权限
        LambdaQueryWrapper<SysPermission> rolePermissQuery = new LambdaQueryWrapper();
        String roleIds = StrUtil.join( "," , roles.stream().map(SysRole::getId).collect(Collectors.toSet()));
        rolePermissQuery.inSql( SysPermission::getId ,
                        StrUtil.format(
                                "select pid from sys_role_permission where rid in ( {} )" ,
                                roleIds )
                );
        List<SysPermission> sysPermissions = permissionMapper.selectList( rolePermissQuery );

        return  sysPermissions.stream()
                .map(SysPermission::getPermissionUrl)
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

}
