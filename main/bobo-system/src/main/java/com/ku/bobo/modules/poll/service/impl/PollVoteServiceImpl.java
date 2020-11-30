package com.ku.bobo.modules.poll.service.impl;

import com.ku.bobo.modules.poll.entity.PollChoice;
import com.ku.bobo.modules.poll.entity.PollVote;
import com.ku.bobo.modules.poll.mapper.PollChoiceMapper;
import com.ku.bobo.modules.poll.mapper.PollVoteMapper;
import com.ku.bobo.modules.poll.payload.vo.PollVO;
import com.ku.bobo.modules.poll.service.IPollService;
import com.ku.bobo.modules.poll.service.IPollVoteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户投票选决 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2020-11-22
 */
@Transactional(  )
@Service
public class PollVoteServiceImpl extends ServiceImpl<PollVoteMapper, PollVote> implements IPollVoteService {

    @Autowired
    private PollChoiceMapper pollChoiceMapper;

    @Autowired
    private IPollService iPollService;

    @Override
    public PollVO saveAndIncrChoiceCountAndGetUpdate(PollVote pollVote) {
        if ( baseMapper.insert( pollVote ) > 0 ){
            //总投票数加一
            pollChoiceMapper.incrChoiceCount( pollVote.getPollChoiceId() );

            return iPollService.getPollVOById( pollVote.getPollId() );
        }
        return null;
    }
}
