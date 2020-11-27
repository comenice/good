package com.ku.bobo.modules.poll.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ku.bobo.middle.common.MiddleUtils;
import com.ku.bobo.modules.poll.entity.Poll;
import com.ku.bobo.modules.poll.entity.PollChoice;
import com.ku.bobo.modules.poll.mapper.PollChoiceMapper;
import com.ku.bobo.modules.poll.mapper.PollMapper;
import com.ku.bobo.modules.poll.mapper.PollVOMapper;
import com.ku.bobo.modules.poll.mapper.PollVoteMapper;
import com.ku.bobo.modules.poll.payload.PollLength;
import com.ku.bobo.modules.poll.payload.PollRequest;
import com.ku.bobo.modules.poll.payload.vo.PollChoiceVO;
import com.ku.bobo.modules.poll.payload.vo.PollVO;
import com.ku.bobo.modules.poll.service.IPollChoiceService;
import com.ku.bobo.modules.poll.service.IPollService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ku.bobo.modules.system.entity.SysUser;
import com.ku.bobo.modules.system.mapper.SysUserMapper;
import com.ku.bobo.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxb
 * @since 2020-11-22
 */
@Slf4j
@Service
public class PollServiceImpl extends ServiceImpl<PollMapper, Poll> implements IPollService {

    @Autowired
    private IPollChoiceService iPollChoiceService;

    @Autowired
    private PollChoiceMapper pollChoiceMapper;

    @Autowired
    private PollVOMapper pollVOMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PollVoteMapper pollVoteMapper;

    @Override
    public void savePollChoices(PollRequest pollRequest) {
        PollLength pollLength = pollRequest.getPollLength();
        Instant now = Instant.now();
        //投票过期时间
        Instant expirationDateTime = now.plus(Duration.ofDays( pollLength.getDays() )).plus(
                Duration.ofHours( pollLength.getHours() )
        );

        Poll poll = new Poll();

        poll.setTitle( pollRequest.getTitle() );
        poll.setCreatedBy( SecurityUtils.getCurrentUserId() );
        poll.setCreatedAt( DateUtil.toLocalDateTime( now ) );
        poll.setExpirationDateTime( expirationDateTime );

        if (  baseMapper.insert(poll) > 0 ){
            pollRequest.getChoices().stream().forEach(
                    pollChoice -> {
                        pollChoice.setPollId( poll.getId() );

                    }
            );
            iPollChoiceService.saveBatch( pollRequest.getChoices() );
        }
    }

    @Override
    public IPage<PollVO> listPollVO(Page page) {

        IPage<PollVO> polls = pollVOMapper.selectPageVO(page);
        if ( polls.getCurrent() < 0 ){
            return null;
        }

        SysUser currentUser = MiddleUtils.getCurrentSysUser(true, true);

        polls.getRecords().forEach( ( poll ) -> {
            //查询投票的选项,选项中包含投票总数
            List<PollChoiceVO> pollChoices = pollChoiceMapper.selectListVO( poll.getId() );
            if ( pollChoices.isEmpty() ){
                //选项丢失
                log.warn( "投票选项丢失，投票ID = {}" , poll.getId() );
                return;
            }

            if ( poll.getCreatedBySysUser() == null || poll.getCreatedBySysUser().getId() == null ){
                log.warn( "投票创建者未找到，创建者ID = {}" , poll.getCreatedBySysUser().getId() );
            }

            poll.setSelectedChoice(null);
            //当前登陆用户是否有选中的选项
            if ( currentUser != null ){
                for (PollChoiceVO pollChoiceVO : pollChoices) {
                    if ( pollChoiceVO.getUserId() != null && pollChoiceVO.getUserId().equals( currentUser.getId() ) )
                        poll.setSelectedChoice(pollChoiceVO.getId());
                        break;
                }
            }

            poll.setChoices( pollChoices );
            poll.setIsExpired( poll.getExpirationDateTime().isBefore( Instant.now()) );
            poll.setTotalVotes( pollChoices.stream().mapToLong( PollChoice::getVoteCount ).sum() );
            poll.setCreationDateTime( poll.getCreatedAt().toInstant(ZoneOffset.UTC) );
        });
        System.out.println("polls = " + polls);
        return polls;
    }


}
