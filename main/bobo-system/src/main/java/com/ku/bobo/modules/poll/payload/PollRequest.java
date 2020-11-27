package com.ku.bobo.modules.poll.payload;

import com.ku.bobo.modules.poll.entity.PollChoice;
import lombok.Data;

import java.util.List;

/**
 * @Date 2020/11/23 10:44
 * @Created by xb
 */
@Data
public class PollRequest {

    private String title;

    private List<PollChoice> choices;

    private PollLength pollLength;

}
