package com.ku.bobo.generator;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.ku.bobo.generator.mysql.dao.IllnessDao;
import com.ku.bobo.generator.mysql.entity.Illness;
import com.ku.bobo.generator.oracle.dao.GyJbbmHnDao;
import com.ku.bobo.generator.oracle.entity.GyJbbmHn;
import com.ku.bobo.common.db.DBInfo;
import com.ku.bobo.common.db.DBOperation;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Date 2020/11/17 11:17
 * @Created by xb
 */
public class test {

    static Integer count = 0;

    public static void main(String[] args) throws IOException {

        DBInfo mysql = new DBInfo(
                "jdbc:mysql://172.21.1.103:3306/big?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useSSL=false",
                "root",
                "123456",
                "com.mysql.jdbc.Driver"
        );

        DBInfo oracle = new DBInfo(
                "jdbc:oracle:thin:@111.22.0.201:10521:kf3bora",
                "testhis2",
                "testhis2",
                "oracle.jdbc.driver.OracleDriver"
        );
        DBOperation mysqlOperation = new DBOperation( mysql );
        mysqlOperation.connection();
        DBOperation oracleOperation = new DBOperation( oracle );
        oracleOperation .connection();


        MybatisConfiguration conf = new MybatisConfiguration();

        String resouce = "mybatis-config.xml";
        InputStream is = Resources.getResourceAsStream(resouce);

        // 构建sqlSession工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build( is );
        SqlSession mysqlSession =  sqlSessionFactory.openSession( mysqlOperation.getCon() );
        SqlSession oracleSession =  sqlSessionFactory.openSession( oracleOperation.getCon() );

        IllnessDao illnessDao = mysqlSession.getMapper(IllnessDao.class);

        List<GyJbbmHn> gyJbbmHns = oracleSession.getMapper(GyJbbmHnDao.class).selectByExample(null);

        AtomicInteger s = new AtomicInteger(0);
        for (int i = 0; i < 5; i++) {
            ThreadUtil.newExecutor().submit(new Runnable() {
                @Override
                public void run() {
                    int j = s.getAndAdd(1);
                    System.out.println( count++ );
                    System.out.println("j = " + j);
                    for (int i = j*200; i < (j+1)*200; i++) {
                        Illness illness = new Illness();
                        illness.setName( gyJbbmHns.get(i).getName() );
                        illness.setLevel(RandomUtil.randomEle(Arrays.asList( "一般","中等" ) ));
                        illness.setPeopleCount( RandomUtil.randomInt(100,500) );
                        illness.setTreatmentRate( RandomUtil.randomDouble(0,1) );
                        illness.setDieRate( RandomUtil.randomDouble(0,1) );
                        illness.setRecurRate( RandomUtil.randomDouble(0,1) );
                        illness.setCause( "占无" );
                        illness.setSymptom( "三月，四月，十二月" );
                        illness.setTallCrowd( "0-10岁,无业。六十以上,退休人员" );
                        illness.setLowCrowd( "16-40岁，大多数职业" );
                        illnessDao.insert( illness );
                    }
                }
            });
        }

    }

}
