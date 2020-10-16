package com.study.usefulknowledge.UI.通讯系统;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Vector;

public class addfriend extends JPanel {
    private static final long serialVersionUID=1L;
    static JTextField nameinput;
    static final int WIDTH=600;
    static final int HEIGHT=300;
    static JTextField departmentinput;
    static JTextField sexinput;
    static JTextField addressinput;
    static JTextField birthdayinput;
    static JTextField dutyinput;
    static JTextField salaryinput;
    static JTextField telinput;
    static JTextField motelinput;
    JFrame frame;
    //    static classmate ss;
    public void add(Component c, GridBagConstraints constraints, int x, int y, int w, int h){
        constraints.gridx=x;
        constraints.gridy=y;
        constraints.gridwidth=w;
        constraints.gridheight=h;
        add(c,constraints);
    }
    addfriend(){
        frame=new JFrame("朋友信息添加窗口");
        frame.setContentPane(this);
        int screemWidth=(int)this.getToolkit().getScreenSize().getWidth();//screemwidth
        int screemHeight=(int)this.getToolkit().getScreenSize().getHeight();
        frame.setLocation((screemWidth-WIDTH)/2,(screemHeight-HEIGHT)/2);//设置窗口位置，离左边(screemWidth-width)/2，上边像素
        frame.setResizable(true);  //设置窗口大小不可调节
        frame.setSize(WIDTH,HEIGHT);//窗口大小，像素
        frame.setVisible(true);//显示或隐藏窗口
        GridBagLayout lay=new GridBagLayout();
        setLayout(lay);
        JLabel name=new JLabel("姓名");
        JLabel code=new JLabel("工号");
        JLabel department=new JLabel("部门");
        JLabel sex=new JLabel("性别");
        JLabel address=new JLabel("家庭地址");
        JLabel birthday=new JLabel("出生年月");
        JLabel duty=new JLabel("职位");
        JLabel salary=new JLabel("薪水");
        JLabel tel=new JLabel("公司电话");
        JLabel motel=new JLabel("移动电话");

        final JTextField codeinput=new JTextField(10);
        nameinput=new JTextField(10);
        addressinput=new JTextField(10);
        departmentinput=new JTextField(10);
        sexinput=new JTextField(10);
        birthdayinput=new JTextField(10);
        dutyinput=new JTextField(10);
        salaryinput=new JTextField(10);
        telinput=new JTextField(10);
        motelinput=new JTextField(10);
        JLabel title=new JLabel("朋友基本信息");

        JButton addbutton=new JButton("添加朋友信息数据");
        GridBagConstraints constraints =new GridBagConstraints();
        constraints.fill=GridBagConstraints.NONE;
        constraints.weightx=4;
        constraints.weighty=6;
        add(title,constraints,0,0,4,1);
        add(name,constraints,0,1,1,1);
        add(code,constraints,0,2,1,1);
        add(department,constraints,0,3,1,1);
        add(sex,constraints,0,4,1,1);
        add(nameinput,constraints,1,1,1,1);
        add(codeinput,constraints,1,2,1,1);
        add(departmentinput,constraints,1,3,1,1);
        add(sexinput,constraints,1,4,1,1);
        add(address,constraints,2,1,1,1);
        add(birthday,constraints,2,2,1,1);
        add(duty,constraints,2,3,1,1);
        add(salary,constraints,2,4,1,1);
        add(addressinput,constraints,3,1,1,1);
        add(birthdayinput,constraints,3,2,1,1);
        add(dutyinput,constraints,3,3,1,1);
        add(salaryinput,constraints,3,4,1,1);
        add(tel,constraints,0,5,1,1);
        add(motel,constraints,2,5,1,1);
        add(telinput,constraints,1,5,1,1);
        add(motelinput,constraints,3,5,1,1);
        add(addbutton,constraints,0,6,2,1);
        addbutton.addActionListener(new ActionListener() {
//            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String nametext=nameinput.getText();
                    String codetext=codeinput.getText();
                    String departmenttext=departmentinput.getText();
                    String sextext=sexinput.getText();
                    String birthdaytext=birthdayinput.getText();
                    String addresstext=addressinput.getText();
                    String dutytext=dutyinput.getText();
                    String salarytext=salaryinput.getText();
                    String teltext=telinput.getText();
                    String moteltext=motelinput.getText();
                    companystore store=new companystore();
                    Connection con=store.getConnection();
                    Statement st=con.createStatement();
                    String sql="insert into comfriend values('"+nametext+"','"+codetext+"','"+departmenttext+
                            "','"+sextext+"','"+birthdaytext+"','"+addresstext+"','"+dutytext+"','"+
                            salarytext+"','"+teltext+"','"+moteltext+"')";
                    st.executeUpdate(sql);
                    company ss=new company(nametext);
                    ss.setcode(codetext);
                    ss.setdepartment(departmenttext);
                    ss.setsex(sextext);
                    ss.setbirthday(birthdaytext);
                    ss.setduty(dutytext);
                    ss.setsalary(salarytext);
                    ss.settel(teltext);
                    ss.setmotel(moteltext);
                    Vector vec =new Vector();
                    vec.add(ss);
                    companyinfo.nameinput.addItem(nametext);
                    companyinfo.nameinput.setSelectedItem(nametext);
                    companyinfo.codeinput.setText(codetext);
                    companyinfo.departmentinput.setText(departmenttext);
                    companyinfo.sexinput.setText(sextext);
                    companyinfo.birthdayinput.setText(birthdaytext);
                    companyinfo.dutyinput.setText(dutytext);
                    companyinfo.salaryinput.setText(salarytext);
                }catch (Exception e1){}
//                frame.dispose();
            }
        });
    }

}
