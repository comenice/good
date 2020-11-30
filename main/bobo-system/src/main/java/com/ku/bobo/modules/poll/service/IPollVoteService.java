package com.ku.bobo.modules.poll.service;

import com.ku.bobo.modules.poll.entity.PollVote;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ku.bobo.modules.poll.payload.vo.PollVO;

/**
 * <p>
 * 用户投票选决 服务类
 * </p>
 *
 * @author zxb
 * @since 2020-11-22
 */
public interface IPollVoteService extends IService<PollVote> {

    /**
     * 保存用户投票选项，并且该选项总票数加1
     * @param pollVote
     */
    PollVO saveAndIncrChoiceCountAndGetUpdate(PollVote pollVote );

}
