package com.study.usefulknowledge.MysqlTest;


import java.sql.*;
import java.util.List;

public class DBUtil
{
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/student?characterEncoding=UTF-8";
    private static final String UNAME = "root";
    private static final String PWD = "root";
    private static Connection connection = null;
    static
    {
        try
        {
            // 1.加载驱动程序   要有mysql-connector-java包
            Class.forName("com.mysql.jdbc.Driver");
            // 2.获得数据库的连接
            connection = DriverManager.getConnection(URL, UNAME, PWD);

            Statement statement=connection.createStatement();
            //执行查询语句，得到查询结果
            String date = new DateTime().toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
            ResultSet resultSet = statement.executeQuery("SELECT * FROM NMC_STATION_REAL WHERE id > 0");
            String sql = "SELECT * FROM NMC_STATION_REAL WHERE  otime LIKE '%"+"2018-08-02 16"+"%' AND stationcode LIKE '%"+stationcode+"%'" ;  //+" AND stationcode LIKE "+"'%"+stationCode+"%'"
            ResultSet resultSets = statement.executeQuery(sql);
            resultSet.last();
            List list = null;
            if (resultSets.getRow()>0)list = convertList(resultSet);

            System.out.println();
            //遍历输出查询结果，一行一行读
//            resultSet.last();
//            int dataLen = resultSet.getRow();
            //  增删改查,见 GoddessDao ,
            //      注意:增的时候,不需要考虑id,其他时候需要
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static Connection getConnection()
    {
        return connection;
    }
}
