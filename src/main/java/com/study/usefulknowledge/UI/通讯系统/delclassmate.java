package com.study.usefulknowledge.UI.通讯系统;

import java.sql.Connection;
import java.sql.Statement;

public class delclassmate {
    delclassmate(){
        String namestring=(String)classinfo.nameinput.getSelectedItem();
        classmatestore store=new classmatestore();
        try{
            Connection con=store.getConnection();
            Statement st=con.createStatement();
            String sql="delete from classmate where name='"+namestring+"'";
            st.executeUpdate(sql);

            classinfo.nameinput.removeItem(namestring);
//            classinfo.nameinput.setSelectedItem(null);
//            classinfo.sexyinput.setText(null);
//            classinfo.addressinput.setText(null);
//            classinfo.homeaddressinput.setText(null);
//            classinfo.companyinput.setText(null);
//            classinfo.dutyinput.setText(null);
//            classinfo.salaryinput.setText(null);
        }catch (Exception e){
            classinfo.nameinput.removeItem(namestring);
        }
    }
}
