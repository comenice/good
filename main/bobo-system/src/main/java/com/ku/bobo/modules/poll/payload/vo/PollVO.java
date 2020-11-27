package com.ku.bobo.modules.poll.payload.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ku.bobo.modules.poll.entity.Poll;
import com.ku.bobo.modules.poll.entity.PollChoice;
import com.ku.bobo.modules.system.entity.SysUser;
import lombok.Data;

import java.time.Instant;
import java.util.List;

/**
 * @Date 2020/11/23 15:22
 * @Created by xb
 */

@Data
@TableName( value = "poll" )
public class PollVO extends Poll {

    @TableField(exist = false)
    private List<PollChoiceVO> choices;

    @TableField
    private SysUser createdBySysUser;

    @TableField(exist = false)
    private Instant creationDateTime;

    @TableField(exist = false)
    private Boolean isExpired;

    @TableField(exist = false)
    private Long selectedChoice;
    @TableField(exist = false)
    private Long totalVotes;

    public PollVO() {

    }
}
