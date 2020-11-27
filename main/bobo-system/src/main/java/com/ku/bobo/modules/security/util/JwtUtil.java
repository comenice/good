package com.ku.bobo.modules.security.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.ku.bobo.api.CodeInfoEnum;
import com.ku.bobo.constant.RedisConstant;
import com.ku.bobo.modules.security.config.JwtProperties;
import com.ku.bobo.exception.AuthException;
import com.ku.bobo.modules.security.service.dto.AuthUserDto;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Date 2020/11/2 15:06
 * @Created by xb
 */
@EnableConfigurationProperties(JwtProperties.class)
@Configuration
@Slf4j
public class JwtUtil {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    private final String authoritiesKey = "ak";

    /**
     * 创建JWT
     *
     * @param rememberMe  记住我
     * @param id          用户id
     * @param subject     用户名
     * @param roles       用户角色
     * @param authorities 用户权限
     * authorities在claim中存取每次都需要转换(不转换取出时很奇怪..),可以考虑直接用string集合存储权限信息,而不一定要遵循GrantedAuthority接口
     * @return JWT
     */
    public String createJWT(Boolean rememberMe, Long id, String subject, List<String> roles, Collection<? extends GrantedAuthority> authorities) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                .setId(id.toString())
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getKey())
                //.claim("roles", roles)
                //原本的authorities在claim存储会出现问题,由此转成string集合,取出时在解析转回
                .claim(authoritiesKey ,authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        // 设置过期时间
        Long ttl = rememberMe ? jwtProperties.getRemember() : jwtProperties.getTtl();
        if (ttl > 0) {
            builder.setExpiration(DateUtil.offsetMillisecond(now, ttl.intValue()));
        }

        String jwt = builder.compact();
        // 将生成的JWT保存至Redis
        stringRedisTemplate.opsForValue()
                .set(RedisConstant.REDIS_JWT_KEY_P + subject, jwt, ttl, TimeUnit.MILLISECONDS);
        return jwt;
    }

    /**
     * 创建JWT
     *
     * @param authentication 用户认证信息
     * @param rememberMe     记住我
     * @return JWT
     */
    public String createJWT(Authentication authentication, Boolean rememberMe) {
        AuthUserDto userPrincipal = (AuthUserDto) authentication.getPrincipal();
        return createJWT(rememberMe, userPrincipal.getId(),
                userPrincipal.getUsername(),null ,
                userPrincipal.getAuthorities());
    }

    /**
     * 解析JWT
     *
     * @param jwt JWT
     * @return {@link Claims}
     */
    public Claims parseJWT(String jwt) throws AuthException {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getKey())
                    .parseClaimsJws(jwt)
                    .getBody();

            String username = claims.getSubject();
            String redisKey = RedisConstant.REDIS_JWT_KEY_P + username;

            // 校验redis中的JWT是否存在
            Long expire = stringRedisTemplate.getExpire(redisKey, TimeUnit.MILLISECONDS);
            if (Objects.isNull(expire) || expire <= 0) {
                throw new AuthException(CodeInfoEnum.TOKEN_EXPIRED);
            }

            // 校验redis中的JWT是否与当前的一致，不一致则代表用户已注销/用户在不同设备登录，均代表JWT已过期
            String redisToken = stringRedisTemplate.opsForValue()
                    .get(redisKey);

            if (!StrUtil.equals(jwt, redisToken)) {
                throw new AuthException(CodeInfoEnum.TOKEN_OUT_OF_CTRL);
            }
            return claims;
        } catch (Exception e) {
            log.error( "token: {} " , e );
            throw new AuthException(CodeInfoEnum.TOKEN_FAILED);
        }
    }

    /**
     * 根据token获取鉴权信息
     * @param token
     * @return
     */
    public Authentication getAuthentication(String token) throws AuthException {
        Claims claims = parseJWT(token);

        ArrayList<String> list = (ArrayList) claims.get( authoritiesKey );
        List<GrantedAuthority> authorityList = list.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        //目前没有在jwt中存储角色信息
        AuthUserDto principal = new AuthUserDto();
        principal.setUsername( claims.getSubject() );
        principal.setId( Long.valueOf( claims.getId() ) );
        principal.setAuthorityList( authorityList );
        return new UsernamePasswordAuthenticationToken(principal, token, authorityList);
    }

    public Authentication getAuthentication(HttpServletRequest request) throws AuthException {
        String jwt = getJwtFromRequest(request);
        if (StringUtils.isEmpty( jwt )){
            return null;
        }
        return getAuthentication( jwt );
    }

        /**
         * 设置JWT过期
         * @param request 请求
         */
    public void invalidateJWT(HttpServletRequest request) {
        String jwt = getJwtFromRequest(request);
        String username = getUsernameFromJWT(jwt);
        // 从redis中清除JWT
        stringRedisTemplate.delete(RedisConstant.REDIS_JWT_KEY_P+ username);
    }

    /**
     * 根据 jwt 获取用户名
     *
     * @param jwt JWT
     * @return 用户名
     */
    public String getUsernameFromJWT(String jwt) {
        Claims claims = parseJWT(jwt);
        return claims.getSubject();
    }

    /**
     * 从 request 的 header 中获取 JWT
     *
     * @param request 请求
     * @return JWT
     */
    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (!StrUtil.isEmpty(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }



}