package com.ku.bobo.modules.poll.mapper;

import com.ku.bobo.modules.poll.entity.PollVote;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ku.bobo.modules.poll.payload.PollVoteCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;

/**
 * <p>
 * 用户投票选决 Mapper 接口
 * </p>
 *
 * @author zxb
 * @since 2020-11-22
 */
public interface PollVoteMapper extends BaseMapper<PollVote> {


}
