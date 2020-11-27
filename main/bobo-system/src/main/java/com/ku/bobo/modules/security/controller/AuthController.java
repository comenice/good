package com.ku.bobo.modules.security.controller;

import com.ku.bobo.api.CommonResult;
import com.ku.bobo.api.ResponseResult;
import com.ku.bobo.constant.HintInfoConstant;
import com.ku.bobo.modules.security.CurrentUser;
import com.ku.bobo.modules.security.config.SecurityConfig;
import com.ku.bobo.modules.security.service.dto.AuthUserDto;
import com.ku.bobo.modules.security.service.vo.JwtUserVo;
import com.ku.bobo.modules.security.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2020/11/2 11:36
 * @Created by xb
 */
@Controller
@Slf4j
@RequestMapping("/auth")
public class AuthController  {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping("/login")
    @ResponseBody
    public CommonResult login(@Validated @RequestBody AuthUserDto user ){

        /**
         * 组装用户认证，包括密码验证方式 {@link SecurityConfig(HttpSecurity)}
         */
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user.getUsername(),user.getPassword()
        );

        //认证用户
        Authentication authenticate = null;
        try {
            authenticate = authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            log.error( e.getMessage() );
            return CommonResult.failed(HintInfoConstant.User.ERROR);
        }
        System.out.println( "认证成功" );
        //保存在认证上下文
        SecurityContextHolder.getContext()
                    .setAuthentication(authenticate);

        AuthUserDto authUserDto = (AuthUserDto) authenticate.getPrincipal();

        String jwt = jwtUtil.createJWT(authenticate, false);

        // 返回 token 与 用户信息
        return CommonResult.success( new JwtUserVo( authUserDto,jwt ));
    }
}
