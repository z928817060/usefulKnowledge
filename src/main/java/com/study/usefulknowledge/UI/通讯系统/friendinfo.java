package com.study.usefulknowledge.UI.通讯系统;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.util.Vector;

public class friendinfo extends JPanel {
    private static final long serialVersionUID=1L;
    String sql="select * from comfriend";
    static JComboBox nameinput;
    static JTextField sexyinput;
    static JTextField birthdayinput;
    static JTextField addressinput;
    static JTextField companyinput;
    static JTextField dutyinput;
    static JTextField salaryinput;
    friendstore store=new friendstore();  //两行建立数据库的连接
    Connection con=store.getConnection();
    public void add(Component c,GridBagConstraints constraints,int x,int y,int w,int h){
        constraints.gridx=x;
        constraints.gridy=y;
        constraints.gridwidth=w;
        constraints.gridheight=h;
        add(c,constraints);
    }
    friendinfo(){
        GridBagLayout lay=new GridBagLayout();
        setLayout(lay);
        JLabel name=new JLabel("姓名");
        JLabel sex=new JLabel("性别");
        JLabel birthday=new JLabel("出生年月");
        JLabel address=new JLabel("家庭地址");
        JLabel company=new JLabel("所在公司");
        JLabel duty=new JLabel("职位");
        JLabel salary=new JLabel("薪水");
        nameinput=new JComboBox();
        salaryinput=new JTextField(10);
        sexyinput=new JTextField(10);
        birthdayinput=new JTextField(10);
        addressinput=new JTextField(10);
        companyinput=new JTextField(20);
        dutyinput=new JTextField(10);

        JLabel title=new JLabel("朋友基本信息");
        GridBagConstraints constraints=new GridBagConstraints();
        constraints.fill=GridBagConstraints.NONE;
        constraints.weightx=4;
        constraints.weighty=6;
        add(title,constraints,0,0,4,1);
        add(name,constraints,0,1,1,1);
        add(sex,constraints,0,2,1,1);
        add(birthday,constraints,0,3,1,1);
        add(address,constraints,0,4,1,1);
        add(nameinput,constraints,1,1,1,1);
        add(sexyinput,constraints,1,2,1,1);
        add(birthdayinput,constraints,1,3,1,1);
        add(addressinput,constraints,1,4,1,1);
        add(company,constraints,2,1,1,1);
        add(duty,constraints,2,2,1,1);
        add(salary,constraints,2,3,1,1);
        add(companyinput,constraints,3,1,1,1);
        add(dutyinput,constraints,3,2,1,1);
        add(salaryinput,constraints,3,3,1,1);
        Vector vec=store.getfriend(con,sql);
        for(int i=0;i<vec.size();i++){ //获取所有name数据
            friend one =(friend) vec.get(i);
            String nameselect=one.getname();
            nameinput.addItem(nameselect);
        }
        nameinput.setEditable(true);  //初始化设置
        String s="请选择您要的姓名信息";
        ComboBoxEditor editor=nameinput.getEditor();
        nameinput.configureEditor(editor,s);
//        String namestring =(String)nameinput.getSelectedItem();  //初始化
//        String namestring =(String)nameinput.getSelectedItem();  //初始化
//        friend p=store.getobject(con,namestring);
//        String inputsex=p.getsex();
//        String inputbirthday=p.getbirthday();
//        String inputaddress=p.getaddtess();
//        String inputcompany=p.getcompany();
//        String inputduty=p.getduty();
//        String inputsalary=p.getsalary();
//        sexyinput.setText(inputsex);
//        birthdayinput.setText(inputbirthday);
//        addressinput.setText(inputaddress);
//        companyinput.setText(inputcompany);
//        dutyinput.setText(inputduty);
//        salaryinput.setText(inputsalary);
        nameinput.addItemListener(new ItemListener() {//选择值改变的事件
            @Override
            public void itemStateChanged(ItemEvent e) {
                String namestring=(String)nameinput.getSelectedItem();
                friend p=store.getobject(con,namestring);
                String inputsex=p.getsex();
                String inputbirthday=p.getbirthday();
                String inputaddress=p.getaddtess();
                String inputcompany=p.getcompany();
                String inputduty=p.getduty();
                String inputsalary=p.getsalary();
                sexyinput.setText(inputsex);
                birthdayinput.setText(inputbirthday);
                addressinput.setText(inputaddress);
                companyinput.setText(inputcompany);
                dutyinput.setText(inputduty);
                salaryinput.setText(inputsalary);
            }
        });
    }

}
