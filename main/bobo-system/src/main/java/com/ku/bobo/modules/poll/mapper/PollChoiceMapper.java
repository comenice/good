package com.ku.bobo.modules.poll.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ku.bobo.modules.poll.entity.PollChoice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ku.bobo.modules.poll.payload.vo.PollChoiceVO;
import com.ku.bobo.modules.poll.payload.vo.PollVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * 投票选项及内容 Mapper 接口
 * </p>
 *
 * @author zxb
 * @since 2020-11-22
 */
public interface PollChoiceMapper extends BaseMapper<PollChoice> {

    @Select("select pc.*,pv.user_id from poll_choice pc LEFT JOIN poll_vote pv " +
            "on pc.id = pv.poll_choice_id " +
            "where pc.poll_id = #{pollId}")
    List<PollChoiceVO> selectListVO( @Param("pollId") Long pollId );


    /**
     * 投票数量加一
     * @param id 投票选项ID
     * @return
     */
    @Update( "update poll_choice set vote_count=vote_count+1 where id = #{id}" )
    int incrChoiceCount( @Param("id") Long id );

}
