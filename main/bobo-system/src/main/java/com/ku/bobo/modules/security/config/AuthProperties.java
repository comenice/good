package com.ku.bobo.modules.security.config;

import com.ku.bobo.middle.common.MiddleUtils;
import com.ku.bobo.modules.security.CurrentUser;
import com.ku.bobo.util.SecurityUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.List;

/**
 * @Date 2020/11/2 17:18
 * @Created by xb
 * 配置需要忽略的路径
 * bobo:
 *   auth:
 *     ignores:
 *       # 需要忽略的 post 请求
 *       post:
 *         - "/auth/login"
 *       # 需要忽略的请求，不限方法
 *       pattern:
 *         - "/test/*"
 */
@Data
@ConfigurationProperties( prefix = "bobo.auth")
public class AuthProperties {

    /**
     * 忽略的请求无法通过以下手段获取用户信息
     * {@link SecurityUtils,CurrentUser}
     */
    private AuthIgnores ignores;

    /**
     * 有一种情况，一个接口允许匿名访问，但在登陆情况下访问需要获取当前用户。
     *
     * 比如，查看投票信息，在未登录时允许查看所有的投票信息（包含投票选项及票数..）。
     * 在登陆的情况下也一样，但需要判断当前用户是否有投过票，对已经投过的选项高亮显示...
     * 未通过weakIgnores实现 todo。
     *
     * 可通过下方法达到同样效果
     * {@link MiddleUtils#getCurrentSysUser(boolean, boolean)}
     */
    private List<String> weakIgnores;

}
