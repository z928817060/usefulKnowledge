package com.study.usefulknowledge.MysqlTest;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBMysqlUtil {
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String dbDriver = null;
    private String dbConnectionURL = null;
    private String dbUsername = null;
    private String dbPassword = null;
    private Logger logger = Logger.getLogger(DBMysqlUtil.class);

    public DBMysqlUtil() {
        dbDriver = PropertyUtil.getProperty("Driver");
        dbConnectionURL = PropertyUtil.getProperty("ConnectionURL");
        dbUsername = PropertyUtil.getProperty("Username");
        dbPassword = PropertyUtil.getProperty("Password");
    }

    public DBMysqlUtil(String dbDriver, String dbConnectionURL, String dbUsername, String dbPassword) {
        this.dbDriver = dbDriver;
        this.dbConnectionURL = dbConnectionURL;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    /**
     * 功能：获取数据库连接
     */
    private Connection getConnection() {
        System.out.println("连接地址：" + dbConnectionURL);
        System.out.println("用户名：" + dbUsername);
        System.out.println("密码：" + dbPassword);
        try {
            Class.forName(dbDriver);
            conn = DriverManager.getConnection(dbConnectionURL, dbUsername,
                    dbPassword);
            logger.info("数据库连接成功");
        } catch (Exception e) {
            logger.error("Error: DbUtil.getConnection() 获得数据库链接失败.\r\n链接类型:"
                    + dbDriver + "\r\n链接URL:" + dbConnectionURL + "\r\n链接用户:"
                    + dbUsername + "\r\n链接密码:" + dbPassword, e);
        }
        return conn;
    }

    /**
     * 功能：执行查询语句
     */
    public ResultSet select(String sql) {
        logger.info("Exec select sql:" + sql);
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
        } catch (SQLException e) {
            logger.error("查询数据异常:" + e.getMessage());
        }
        return rs;

    }

    /**
     * 功能：执行查询语句，获取记录数
     */
    public int getRecordCount(String sql) {
        logger.info("Exec getRecordCount sql:" + sql);
        int counter = 0;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                counter++;
            }
        } catch (SQLException e) {
            logger.error("执行DbUtil.getRecordCount()方法发生异常，异常信息：", e);
        } finally {
            close();
        }
        System.out.println("counter总数：" + counter);
        return counter;
    }

    /**
     * 功能:针对单条记录执行更新操作(新增、修改、删除)
     */
    public int executeupdate(String sql) throws Exception {
        logger.info("Exec update sql:" + sql);
        int num = 0;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            num = ps.executeUpdate();
        } catch (SQLException sqle) {
            logger.error("insert/update/delete  data Exception: " +
                    sqle.getMessage());
        } finally {
            close();
        }
        System.out.println("影响条数：" + num);
        return num;
    }

    /**
     * 功能:批量执行SQL(update或delete)
     *
     * @param sqlList sql语句集合
     */
    public int executeBatch(List<String> sqlList) {
        int result = 0;
        for (String sql : sqlList) {
            try {
                result += executeupdate(sql);
            } catch (Exception e) {
                System.out.println("查询异常：" + e.getMessage());
            }
        }
        System.out.println("executeBatch Result:" + result);
        return result;
    }

    /**
     * 功能:关闭数据库的连接
     */
    public void close() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
            logger.info("关闭数据库连接成功");
        } catch (Exception e) {
            logger.error("执行DbUtil.close()方法发生异常，异常信息：", e);
        }
    }

    /**
     * 功能:将mysql的result结果转化为list的map
     * @param rs
     * @return  list<Map<String ,String >>
     * @throws SQLException
     */
    private static List<Map> convertList(ResultSet rs) throws SQLException{
        List list = new ArrayList();
        ResultSetMetaData md = rs.getMetaData();//获取键名
        int columnCount = md.getColumnCount();//获取行的数量
        while (rs.getRow()>0) {
            Map rowData = new HashMap();//声明Map
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));//获取键名及值
            }
            list.add(rowData);
            rs.next();
        }
        return list;
    }
    /**
     * 功能:将mysql的result结果转化为json
     * @param rs
     * @return  list<json>
     * @throws SQLException
     */
    public static List<JSONObject> convertListJson(ResultSet rs) throws SQLException{
        List list = new ArrayList();
        ResultSetMetaData md = rs.getMetaData();//获取键名
        int columnCount = md.getColumnCount();//获取行的数量
        while (rs.getRow()>0) {
            JSONObject json = new JSONObject();  //json
            for (int i = 1; i <= columnCount; i++) {
                json.put(md.getColumnName(i), rs.getObject(i));//获取键名及值
            }
            list.add(json);
            rs.next();
        }
        return list;
    }
}
