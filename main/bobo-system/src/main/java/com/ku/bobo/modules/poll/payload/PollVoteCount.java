package com.ku.bobo.modules.poll.payload;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ku.bobo.modules.poll.entity.PollVote;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户投票选决 分组(pollChoiceId) 总数
 * </p>
 *
 * @author zxb
 * @since 2020-11-22
 */
@Data
public class PollVoteCount extends PollVote {

    private Long count;

}
