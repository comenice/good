package com.ku.bobo.modules.security.service.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ku.bobo.modules.security.service.dto.AuthUserDto;
import lombok.Data;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @Date 2020/11/9 17:13
 * @Created by xb
 */
@Data
public class JwtUserVo   {

    private String token;
    private User user;

    @Data
    class User {
        private String username;
        public User(AuthUserDto authUserDto) {
            this.username = authUserDto.getUsername();
        }
    }

    public JwtUserVo(AuthUserDto authUserDto,String token) {
        this.token = token;
        this.user = new User( authUserDto );
    }
}
