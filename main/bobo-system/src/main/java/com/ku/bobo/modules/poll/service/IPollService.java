package com.ku.bobo.modules.poll.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ku.bobo.modules.poll.entity.Poll;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ku.bobo.modules.poll.payload.PollRequest;
import com.ku.bobo.modules.poll.payload.vo.PollVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxb
 * @since 2020-11-22
 */
public interface IPollService extends IService<Poll> {

   void savePollChoices(PollRequest pollRequest);

   IPage<PollVO> listPollVO(Page page);

}
