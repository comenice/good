package com.ku.bobo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication( scanBasePackages = "com.ku.bobo.**")
@CrossOrigin
@MapperScan( "com.ku.bobo.modules.*.mapper" )
    public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
