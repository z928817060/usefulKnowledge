package com.study.usefulknowledge;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @title 20个java常用代码 
 * @desc jdbc连接串(五)
 * @create 20130708
 * @author usefulknowledge
 */
public class JdbcConn {
	private final int ORACLE=1;
	private final int MYSQL=2;
	Connection con;
	public void init(int type){
		
		if(type==ORACLE){
			try {
				initOracle(null);
			} catch (FileNotFoundException e) {
				System.out.println("没有找到驱动文件");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(type==MYSQL)
			initMysql();
	}
	
	public void initMysql(){
		String className="com.mysql.jdbc.Driver";
		String url="jdbc:mysql://localhost/edinner";
		String userName="root";
		String password="root";
		try {
			Class.forName(className);
			con=DriverManager.getConnection(url,userName,password);
			
		} catch (ClassNotFoundException e) {
			System.out.println("加载驱动文件失败");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void initOracle

	(FileInputStream fs) throws ClassNotFoundException,

	SQLException, FileNotFoundException, IOException

	{String driverClass = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@10.46.9.233:1521:soadbfj";
	String userName = "ngnmc";
	String password = "ng34nmc#";
		/*
		 * Properties props = new Properties();
		 * 
		 * props.load(fs);
		 * 
		 * String url = props.getProperty
		 * 
		 * ("db.url"); String
		 * 
		 * userName = props.getProperty("db.user");
		 * 
		 * String password = props.getProperty
		 * 
		 * ("db.password");
		 */
		Class.forName(driverClass);

		con = DriverManager.getConnection(url,

		userName, password);
	}

	public void fetch() throws SQLException,

	IOException {

		PreparedStatement ps =

		con.prepareStatement("select SYSDATE from dual");

		ResultSet rs = ps.executeQuery

		();

		while

		(rs.next()) {

		Date date=	rs.getDate("SYSDATE");
		System.out.println(date.getDate());

		}

		rs.close();

		ps.close();

	}
	public void fetch2() throws SQLException,

	IOException {

		PreparedStatement ps =

		con.prepareStatement("select name from t_user");

		ResultSet rs = ps.executeQuery

		();

		while

		(rs.next()) {

		String name=rs.getString("name");
System.out.println(name);
		}

		rs.close();

		ps.close();

	}


	public static void main(String[] args) {

		JdbcConn test = new JdbcConn();
		try {
			test.initOracle(null);
			test.fetch();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		test.init(2);
		try {
			test.fetch2();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
