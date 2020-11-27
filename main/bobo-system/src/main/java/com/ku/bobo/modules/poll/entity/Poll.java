package com.ku.bobo.modules.poll.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.Instant;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.ku.bobo.exception.Asserts;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zxb
 * @since 2020-11-22
 */
@Data
@ApiModel(value="Poll对象", description="")
public class Poll implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String title;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long createdBy;

    private Long updatedBy;

    private Instant expirationDateTime;


    public static Poll createPoll ( Object o ){
        if ( o instanceof Poll ){
            return (Poll) o;
        }else {
            Asserts.fail( "类型转换出现问题: " + o.getClass() + "不能转换成" + Poll.class.getName() ,true);
            return null; //todo Asserts.fail一定会抛出异常，这一步一定不会执行。  编译器问题?
        }
    }

}
