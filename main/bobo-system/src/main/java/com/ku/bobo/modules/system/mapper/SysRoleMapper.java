package com.ku.bobo.modules.system.mapper;

import com.ku.bobo.modules.system.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zxb
 * @since 2020-10-30
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select( "select r.* from sys_role r , sys_user_role u where r.ID = u.RID and u.UID = #{id}" )
    Set<SysRole> findByUserId( Long id );

}
