package com.ku.bobo.modules.poll.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.ku.bobo.middle.config.mp.PropDV;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 投票选项及内容
 * </p>
 *
 * @author zxb
 * @since 2020-11-22
 */
@Data
@PropDV(
    name = { "voteCount" },
    value = { "0" },
    type = { Integer.class }
)
@ApiModel(value="PollChoice对象", description="投票选项")
public class PollChoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String text;

    private Long pollId;

    @TableField(fill = FieldFill.INSERT)
    private Integer voteCount;
}
