package com.ku.bobo.common.db;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ReUtil;
import cn.hutool.http.HttpUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Date 2020/10/26 16:08
 * @Created by xb
 */
@Getter
@Setter
public class DBInfo implements Serializable {

    private String jdbcUrl;

    private String userName;

    private String passWord;

    private String dateName;

    public DBInfo(String jdbcUrl, String userName, String passWord, String dateName) {
        this.jdbcUrl = jdbcUrl;
        this.userName = userName;
        this.passWord = passWord;
        this.dateName = dateName;
    }


    public static void main(String[] args) {

      String listContent = HttpUtil.get("https://data.stats.gov.cn/search.htm?s=%E7%96%BE%E7%97%85%E5%88%97%E8%A1%A8%3Dsearchdata&m=searchdata&db=&p=2");
        //使用正则获取所有标题
        System.out.println( "listContent" + listContent );
        List<String> titles = ReUtil.findAll("<span>(.*?)</span>", listContent, 1);
        for (String title : titles) {
            //打印标题
            Console.log(title);
        }

    }















}
