package com.study.usefulknowledge.UI.通讯系统;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class companystore {
    public Vector getcompany(Connection con, String sql){
        Vector v=new Vector();
        try{
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery(sql);
            while(rs.next()){
                String name=rs.getString(1);
                company c1=new company(name);
                String code=rs.getString(2);
                String department=rs.getString(3);
                String sex=rs.getString(4);
                String address=rs.getString(5);
                String birthday=rs.getString(6);
                String duty=rs.getString(7);
                String salary=rs.getString(8);
                String tel=rs.getString(9);
                String motel=rs.getString(10);
                c1.setcode(code);
                c1.settel(tel);
                c1.setdepartment(department);
                c1.setaddtess(address);
                c1.setsex(sex);
                c1.setduty(duty);
                c1.setsalary(salary);
                c1.setbirthday(birthday);
                c1.setmotel(motel);
                v.add(c1);
            }
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return v;
    }
    public company getobject(Connection con,String name){
        company c=null;
        try{
            Statement st=con.createStatement();
            String sql="select*from company where name="+"'"+name+"'";//有个单引号？？
            ResultSet rs=st.executeQuery(sql);
            while (rs.next()){
                name=rs.getString(1);
                String code=rs.getString(2);
                String department=rs.getString(3);
                String sex=rs.getString(4);
                String address=rs.getString(5);
                String birthday=rs.getString(6);
                String duty=rs.getString(7);
                String salary=rs.getString(8);
                String tel=rs.getString(9);
                String motel=rs.getString(10);
                c=new company(name);
                c.setcode(code);
                c.settel(tel);
                c.setdepartment(department);
                c.setaddtess(address);
                c.setsex(sex);
                c.setduty(duty);
                c.setsalary(salary);
                c.setbirthday(birthday);
                c.setmotel(motel);
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
