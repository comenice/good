package com.ku.bobo.modules.poll.service.impl;

import com.ku.bobo.modules.poll.entity.PollChoice;
import com.ku.bobo.modules.poll.mapper.PollChoiceMapper;
import com.ku.bobo.modules.poll.service.IPollChoiceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 投票选项及内容 服务实现类
 * </p>
 *
 * @author zxb
 * @since 2020-11-22
 */
@Service
public class PollChoiceServiceImpl extends ServiceImpl<PollChoiceMapper, PollChoice> implements IPollChoiceService {

}
