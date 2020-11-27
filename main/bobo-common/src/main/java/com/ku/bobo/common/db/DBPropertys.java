package com.ku.bobo.common.db;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Date 2020/10/28 17:17
 * @Created by xb
 */
@SuppressWarnings("all")
@Component
@ConfigurationProperties( prefix = "bobo.db")
public class DBPropertys {

    private String url;

    private String userName;

    private String passWord;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
