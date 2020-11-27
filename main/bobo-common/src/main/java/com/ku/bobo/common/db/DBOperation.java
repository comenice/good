package com.ku.bobo.common.db;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Date 2020/10/26 16:03
 * @Created by xb
 */
public class DBOperation {

    private DataSource dataSource;

    private Connection con;


    public static Boolean testConnection( DBInfo dbInfo ){
        return DBUtil.testConnection( dbInfo );
    }

    public DBOperation( DBInfo dbInfo ){
        dataSource = DBUtil.getDataSource( dbInfo );
    }

    public void connection(){
        con = DBUtil.getConnection( dataSource );
    }

    private void connectionIfNull(){
        if ( null == con ){
            connection();

        }
    }

    public void close(){
            try {
                if ( con != null ){
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    /**
     * 获取当前数据库中所有表信息
     * @return
     */
    public Map<String,Object> getTablesInfo(){
        connectionIfNull();
        Map<String,Object> hashMap = null;
        ResultSet rs = null;
        try {
            DatabaseMetaData meta = con.getMetaData();
            rs = meta.getTables(null, null, null,
                    new String[] { "TABLE" });
            hashMap = new HashMap<String,Object>();
            while (rs.next()) {
                hashMap.put( rs.getString(3) , null );
            }
        } catch (SQLException e) {
            throw new RuntimeException("数据库表信息获取失败:  " + e );
        }finally {
            close( rs );
        }
        return hashMap;
    }

    /**
     * 根据表名获取列信息
     * @param tableName
     * @return
     */
    public Map<String,String> getColumnByTableName( String tableName ){
        connectionIfNull();
        HashMap hashMap = null;
        ResultSet rs = null;
        try {
            DatabaseMetaData metaData = con.getMetaData();
            //局部ResultSet 使用需要关闭
            rs = metaData.getColumns(null, null, tableName, "%");
            hashMap = new HashMap();
            while (rs.next()) {
                //获得字段名
                String columnName = rs.getString("COLUMN_NAME");
                //获得字段类型
                String typeName = rs.getString("TYPE_NAME");
                hashMap.put( columnName,typeName );
            }
        } catch (SQLException e) {
            throw new RuntimeException("数据库表列明信息获取失败:  " + e );
        }finally {
           close(rs);
        }
        return hashMap;
    }

    private void close( ResultSet rs ){
        try {
            if ( rs != null )
                rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Connection getCon() {
        return con;
    }

    public static void main(String[] args) {

        DBInfo mysql = new DBInfo(
                "jdbc:mysql://172.21.1.103:3306/davinci?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useSSL=false",
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
        DBOperation dbOperation = new DBOperation( oracle );
        dbOperation.connection();
        Map<String,Object> map = dbOperation.getTablesInfo();
        Set<String> tables = map.keySet();
        for (String table : tables) {
            System.out.println("table = " + table);
            Map<String, String> colName = dbOperation.getColumnByTableName(table);
            Set<String> colNames = colName.keySet();
            colNames.forEach(System.out::println);
        }
    }
}
