package com.study.usefulknowledge.UI.通讯系统;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class sql {
    private static String url="jdbc:mysql://127.0.0.1:3306/combook";
    private static String name="root";
    private static String password="root";
    public static String sql;
    public static int result;
    public static void main(String[] argd){
        try {
            //1、加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            //2、获取数据库连接
            Connection connection= DriverManager.getConnection(url,name,password);
            //3、！！！实现数据库连接，实现增删改查Statement
            Statement statement=connection.createStatement();
            //4、执行查询语句，得到查询结果
            //删除表tablename1
            sql = "drop table classmate";
            result =statement.executeUpdate(sql);// 将sql语句更新至数据库
            sql = "drop table company";
            result =statement.executeUpdate(sql);// 将sql语句更新至数据库
            sql = "drop table comfriend";
            result =statement.executeUpdate(sql);// 将sql语句更新至数据库
            //创建表goddess
            sql = "create table classmate(name char(20),code char(11),department char(20),sex char(20),address char(20),birthday char(20),duty char(20),salary char(20),tel char(20),motel char(20))";//id,name,mobie,email,address
            result = statement.executeUpdate(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
            sql = "create table company(name char(20),code char(11),department char(20),sex char(20),address char(20),birthday char(20),duty char(20),salary char(20),tel char(20),motel char(20))";//id,name,mobie,email,address
            result = statement.executeUpdate(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
            sql = "create table comfriend(name char(20),code char(11),department char(20),sex char(20),address char(20),birthday char(20),duty char(20),salary char(20),tel char(20),motel char(20))";//id,name,mobie,email,address
            result = statement.executeUpdate(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
            //插入数据
            sql = "insert into classmate(name,code,department,sex,address,birthday,duty,salary,tel,motel) values" +
                    "('classmate周冰','女','上海市浦东','上海市浦东','上海','上海美文图书工作室','图形策划师','10000','3456123-02','137712546')";
            result = statement.executeUpdate(sql); //执行sql语句
            sql = "insert into company(name,code,department,sex,address,birthday,duty,salary,tel,motel) values" +
                    "('company周冰','女','上海市浦东','上海市浦东','上海','上海美文图书工作室','图形策划师','10000','3456123-02','137712546')";
            result = statement.executeUpdate(sql); //执行sql语句
            sql = "insert into comfriend(name,code,department,sex,address,birthday,duty,salary,tel,motel) values" +
                    "('comfriend周冰','女','上海市浦东','上海市浦东','上海','上海美文图书工作室','图形策划师','10000','3456123-02','137712546')";
            result = statement.executeUpdate(sql); //执行sql语句
            //查询的
//            ResultSet resultSet=statement.executeQuery("alter table classmate change q name");
//            //5、遍历输出查询结果，一行一行读
//            while (resultSet.next()){
//                System.out.println(resultSet.getString("name"));
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
