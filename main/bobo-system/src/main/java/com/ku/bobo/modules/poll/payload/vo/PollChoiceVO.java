package com.ku.bobo.modules.poll.payload.vo;

import com.ku.bobo.modules.poll.entity.PollChoice;
import lombok.Data;

/**
 * @Date 2020/11/26 9:06
 * @Created by xb
 */
@Data
public class PollChoiceVO extends PollChoice {

    private Long userId;

}
