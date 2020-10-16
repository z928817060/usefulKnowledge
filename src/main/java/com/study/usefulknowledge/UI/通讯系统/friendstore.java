package com.study.usefulknowledge.UI.通讯系统;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;


/**
 * 数据库读取的例子
 */
public class friendstore {
    public Vector getfriend(Connection con, String sql){
        Vector v=new Vector();
        try{
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                String name=rs.getString(1);
                String sexy=rs.getString(2);
                String birthday=rs.getString(3);
                String address=rs.getString(4);
                String company=rs.getString(5);
                String duty=rs.getString(6);
                String salary=rs.getString(7);
                String tel=rs.getString(8);
                String phone=rs.getString(9);
                friend ss=new friend(name);
                ss.setsexy(sexy);
                ss.settel(tel);
                ss.setcompany(company);
                ss.setaddtess(address);
                ss.setduty(duty);
                ss.setsalary(salary);
                ss.setbirthday(birthday);
                ss.setphone(phone);
                v.add(ss);
            }
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return v;
    }
    public friend getobject(Connection con,String name){
        friend f=null;
        try{
            Statement st=con.createStatement();
            String sql="select*from comfriend where name="+"'"+name+"'";//
            ResultSet rs=st.executeQuery(sql);   //通过ResultSet， while (rs.next())进行所有行的遍历，getString（i）方法获取列的选取
            while (rs.next()){
                name=rs.getString(1);
                String sexy=rs.getString(2);
                String birthday=rs.getString(3);
                String address=rs.getString(4);
                String company=rs.getString(5);
                String duty=rs.getString(6);
                String salary=rs.getString(7);
                String tel=rs.getString(8);
                String phone=rs.getString(9);
                f=new friend(name);
                f.setsexy(sexy);
                f.settel(tel);
                f.setcompany(company);
                f.setaddtess(address);
                f.setduty(duty);
                f.setsalary(salary);
                f.setbirthday(birthday);
                f.setphone(phone);
            }
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return f;
    }
    public Connection getConnection(){
        Connection con=null;
        String url="jdbc:mysql://127.0.0.1:3306/combook";
        String username="root";
        String password="root";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(url,username,password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return con;
    }
}
