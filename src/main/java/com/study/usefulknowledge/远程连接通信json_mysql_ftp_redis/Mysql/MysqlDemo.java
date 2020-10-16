package com.study.usefulknowledge.远程连接通信json_mysql_ftp_redis.Mysql;


/**
 *  sql语法大全 http://www.cnblogs.com/yunf/archive/2011/04/12/2013448.html
 *  sql数据范围写法：http://www.jb51.net/article/57342.htm
 * @author ：陶伟基 ，微博：http://weibo.com/taoandtao
 * @date ：2012/12/11
 * @place：广州大学华软软件学院
 *
 */
import java.sql.*;


public class MysqlDemo {
    public static void main(String[] args) throws Exception {
        Connection conn = null;
        String sql;
        // MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值
        // 避免中文乱码要指定useUnicode和characterEncoding
        // 执行数据库操作之前要在数据库管理系统上创建一个数据库，名字自己定，
        // 下面语句之前就要先创建javademo数据库
//        String url = "jdbc:mysql://localhost:3306/javademo?"
//                + "user=root&password=root&useUnicode=true&characterEncoding=UTF8";
         String url="jdbc:mysql://127.0.0.1:3306/student";
         String name="root";
         String password="root";

        try {
            // 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
            // 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
            Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
            // or:
            // com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
            // or：
            // new com.mysql.jdbc.Driver();

            System.out.println("成功加载MySQL驱动程序");
            // 一个Connection代表一个数据库连接
            conn = DriverManager.getConnection(url,name,password);
            // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
            Statement stmt = conn.createStatement();
            /**
             * 表格的增删
             */
            //删除表tablename1
//            sql = "drop table tablename1";
//            stmt.executeUpdate(sql);// 将sql语句更新至数据库
//            sql = "create table student(NO char(20),name varchar(20),primary key(NO))";
            //创建表goddess
            sql = "create table goddess(id char(20),name char(20),mobie char(11),email char(20),address varchar(20))";//id,name,mobie,email,address
//            int result = stmt.executeUpdate(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
            int result=1;
            if (result != -1) {
                System.out.println("创建数据表成功");
                //增
                sql = "insert into goddess(id,name,mobie,email,address) values('1','陶伟基','138516','123@163.com','北京')";
                result = stmt.executeUpdate(sql); //执行sql语句
                sql = "insert into goddess(2,2) values(1,1)";
                result = stmt.executeUpdate(sql);
                //删
//                sql = "insert into goddess(id,name,mobie,email,address) values('2','小明','1385','123@163.com','东京')";
//                result = stmt.executeUpdate(sql);
                sql = "select * from goddess";   //选择范围
                ResultSet rs = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
                System.out.println("学号\t姓名");
                while (rs.next()) {
                    System.out.println(rs.getString(1) + "\t" + rs.getString(2));// 入如果返回的是int类型可以用getInt()
                }
            }
        } catch (SQLException e) {
            System.out.println("MySQL操作错误");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
                conn.close();
        }

    }

}