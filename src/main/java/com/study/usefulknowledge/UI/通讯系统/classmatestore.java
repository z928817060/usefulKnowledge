package com.study.usefulknowledge.UI.通讯系统;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class classmatestore {
    public Vector getclassmate(Connection con, String sql){
        Vector v=new Vector();
        try{
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                String name=rs.getString(1);
                classmate c1=new classmate(name);
                String sexy=rs.getString(2);
                String address=rs.getString(3);
                String homeaddress=rs.getString(4);
                String city=rs.getString(5);
                String company=rs.getString(6);
                String duty=rs.getString(7);
                String salary=rs.getString(8);
                String contact=rs.getString(9);
                String homephone=rs.getString(10);
                c1.setsexy(sexy);
                c1.setaddtess(address);
                c1.sethomeaddress(homeaddress);
                c1.setcity(city);
                c1.setcompany(company);
                c1.setduty(duty);
                c1.setsalary(salary);
                c1.setcontact(contact);
                c1.sethomephone(homephone);
                v.add(c1);
            }
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return v;
    }
    public classmate getobject(Connection con,String name){
        classmate c=null;
        try{
            Statement st=con.createStatement();
            String sql="select*from classmate where name="+"'"+name+"'";//有个单引号？？
            ResultSet rs=st.executeQuery(sql);
            while (rs.next()){
                name=rs.getString(1);
                String sexy=rs.getString(2);
                String address=rs.getString(3);
                String homeaddress=rs.getString(4);
                String city=rs.getString(5);
                String company=rs.getString(6);
                String duty=rs.getString(7);
                String salary=rs.getString(8);
                String contact=rs.getString(9);
                String homephone=rs.getString(10);
                c=new classmate(name);
                c.setsexy(sexy);
                c.setaddtess(address);
                c.sethomeaddress(homeaddress);
                c.setcity(city);
                c.setcompany(company);
                c.setduty(duty);
                c.setsalary(salary);
                c.setcontact(contact);
                c.sethomephone(homephone);
            }
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return c;
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
