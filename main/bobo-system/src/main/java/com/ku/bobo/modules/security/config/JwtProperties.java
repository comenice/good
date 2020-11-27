package com.ku.bobo.modules.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Date 2020/11/2 15:46
 * @Created by xb
 */
@ConfigurationProperties( prefix = "auth.jwt.config")
@Data
public class JwtProperties {

    /**
     * jwt 加密 key，默认值：kubobo.
     */
    private String key = "kubobo";

    /**
     * jwt 过期时间，默认值：600000 {@code 10 分钟}.
     */
    private Long ttl = 600000L * 100;//1000分钟

    /**
     * 开启 记住我 之后 jwt 过期时间，默认值 604800000 {@code 7 天}
     */
    private Long remember = 604800000L;

}
