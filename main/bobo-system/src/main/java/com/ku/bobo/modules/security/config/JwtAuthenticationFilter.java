package com.ku.bobo.modules.security.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.Sets;
import com.ku.bobo.api.CodeInfoEnum;
import com.ku.bobo.exception.AuthException;
import com.ku.bobo.modules.security.util.JwtUtil;
import com.ku.bobo.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @Date 2020/11/3 14:34
 * @Created by xb
 */
@Component
@Order
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private AuthProperties customConfig;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (  checkIgnores( request ) ){
            filterChain.doFilter( request , response );
            return;
        }

        Authentication authentication = null;
        //由于过滤器中抛出异常并不会被全局捕获,所以局部处理.
        try {
            authentication = jwtUtil.getAuthentication(request);
        } catch (AuthException e) {
            e.printStackTrace();
            ResponseUtils.renderJson( response , e.getICodeInfo() );
            return;
        }
        if ( ObjectUtil.isNotNull( authentication ) ){
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter( request , response );
            return;
        }
        ResponseUtils.renderJson( response , CodeInfoEnum.UNAUTHORIZED );
    }

    /**
     * 由于spring security没有像shiro指定过滤器要过滤的URL，所有默认是过滤所有URL。
     *
     * 白名单URL不进行token验证 {@link AuthProperties}
     * @param request 当前请求
     * @return true - 忽略，false - 不忽略
     */
    private boolean checkIgnores(HttpServletRequest request) {
        String method = request.getMethod();

        System.out.println("当前请求地址 = " + request.getRequestURI());

        HttpMethod httpMethod = HttpMethod.resolve(method);
        if (ObjectUtil.isNull(httpMethod)) {
            httpMethod = HttpMethod.GET;
        }

        Set<String> ignores = Sets.newHashSet();

        switch (httpMethod) {
            case GET:
                ignores.addAll(customConfig.getIgnores().getGet());
                break;
            case PUT:
                ignores.addAll(customConfig.getIgnores().getPut());
                break;
            case HEAD:
                ignores.addAll(customConfig.getIgnores().getHead());
                break;
            case POST:
                ignores.addAll(customConfig.getIgnores().getPost());
                break;
            case PATCH:
                ignores.addAll(customConfig.getIgnores().getPatch());
                break;
            case TRACE:
                ignores.addAll(customConfig.getIgnores().getTrace());
                break;
            case DELETE:
                ignores.addAll(customConfig.getIgnores().getDelete());
                break;
            case OPTIONS:
                ignores.addAll(customConfig.getIgnores().getOptions());
                break;
            default:
                break;
        }

        ignores.addAll(customConfig.getIgnores().getPattern());

        if (CollUtil.isNotEmpty(ignores)) {
            for (String ignore : ignores) {
                AntPathRequestMatcher matcher = new AntPathRequestMatcher(ignore, method);
                if (matcher.matches(request)) {
                    return true;
                }
            }
        }

        return false;
    }

}
