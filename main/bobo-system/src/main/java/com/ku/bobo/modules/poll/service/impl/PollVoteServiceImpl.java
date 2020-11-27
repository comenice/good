package com.ku.bobo.modules.poll.service.impl;

import com.ku.bobo.modules.poll.entity.PollVote;
import com.ku.bobo.modules.poll.mapper.PollVoteMapper;
import com.ku.bobo.modules.poll.service.IPollVoteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户投票选决 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2020-11-22
 */
@Service
public class PollVoteServiceImpl extends ServiceImpl<PollVoteMapper, PollVote> implements IPollVoteService {

}
