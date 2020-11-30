package com.ku.bobo.modules.poll.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ku.bobo.modules.poll.entity.Poll;
import com.ku.bobo.modules.poll.payload.vo.PollVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zxb
 * @since 2020-11-22
 */
public interface PollVOMapper extends BaseMapper<PollVO> {

    @Select("select p.* , su.id as 'createdBySysUser.id' ,su.username as 'createdBySysUser.username' from poll p LEFT JOIN sys_user su on p.created_by = su.id")
    IPage<PollVO> selectPageVO( IPage<PollVO> page );

    @Select("select p.* , su.id as 'createdBySysUser.id' ,su.username as 'createdBySysUser.username' from poll p LEFT JOIN sys_user su on p.created_by = su.id where p.id = #{pollId}")
    PollVO selectVO( @Param("pollId") Long pollId );



}
