package com.ku.bobo.common.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.ku.bobo.enums.DataTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Date 2020/10/26 16:21
 * @Created by xb
 */
@Slf4j
public class DBUtil {

    protected static DataSource getDataSource(DBInfo dbInfo) {
        DruidDataSource druidDataSource = new DruidDataSource();
        String className;
        try {
            className = DriverManager.getDriver(dbInfo.getJdbcUrl().trim()).getClass().getName();
        } catch (SQLException e) {
            throw new RuntimeException("Get class name error: =" + dbInfo.getJdbcUrl());
        }
        if (StringUtils.isEmpty(className)) {
            DataTypeEnum dataTypeEnum = DataTypeEnum.urlOf(dbInfo.getJdbcUrl());
            if (null == dataTypeEnum) {
                throw new RuntimeException("Not supported data type: jdbcUrl=" + dbInfo.getJdbcUrl());
            }
            druidDataSource.setDriverClassName(dataTypeEnum.getDriver());
        } else {
            druidDataSource.setDriverClassName(className);
        }


        druidDataSource.setUrl(dbInfo.getJdbcUrl());
        druidDataSource.setUsername(dbInfo.getUserName());
        druidDataSource.setPassword(dbInfo.getPassWord());
        // 配置获取连接等待超时的时间
        druidDataSource.setMaxWait(3000);
        // 配置初始化大小、最小、最大
        druidDataSource.setInitialSize(1);
        druidDataSource.setMinIdle(1);
        druidDataSource.setMaxActive(1);

        // 如果链接出现异常则直接判定为失败而不是一直重试
        druidDataSource.setBreakAfterAcquireFailure(true);
        try {
            druidDataSource.init();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return druidDataSource;
    }
    protected static Connection getConnection(DBInfo dbInfo) {
        DataSource dataSource = getDataSource(dbInfo);
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (Exception ignored) {}
        try {
            int timeOut = 5;
            if (null == connection || connection.isClosed() || !connection.isValid(timeOut)) {
                log.info("connection is closed or invalid, retry get connection!");
                connection = dataSource.getConnection();
            }
        } catch (Exception e) {
            log.error("create connection error, jdbcUrl: {}", dbInfo.getJdbcUrl());
            throw new RuntimeException("create connection error, jdbcUrl: " + dbInfo.getJdbcUrl());
        }
        return connection;
    }

    protected static Connection getConnection( DataSource dataSource) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (Exception ignored) {}
            int timeOut = 5;
        try {
            if (null == connection || connection.isClosed() || !connection.isValid(timeOut)) {
                log.info("connection is closed or invalid, retry get connection!");
                    connection = dataSource.getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return connection;
    }

    protected static void releaseConnection(Connection connection) {
        if (null != connection) {
            try {
                connection.close();
            } catch (Exception e) {
                log.error(e.getMessage(),e);
                log.error("connection close error：" + e.getMessage());
            }
        }
    }


    protected static void closeResult(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                log.error(e.getMessage(),e);
            }
        }
    }

    protected static boolean testConnection(DBInfo dbInfo) {
        Connection connection = null;
        try {
            connection = getConnection(dbInfo);
            if (null != connection) {
                return true;
            }
        } catch (Exception e) {
            log.info("Get connection failed:" + e.getMessage());
        } finally {
            releaseConnection(connection);
        }
        return false;
    }


}
