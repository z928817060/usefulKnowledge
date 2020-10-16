package com.study.usefulknowledge.UI.通讯系统;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.util.Vector;

public class classmatecommunication {
    private static final long serialVersionUID=1L;
    static JComboBox nameinput;
    String sql="select * from classmate";
    classmatestore store=new classmatestore();
    Connection con=store.getConnection();
    static JTextField moteltext;
    static JLabel name;
    static JLabel title;
    static JLabel title1;
    static JButton closebutton;
    static JPanel pane;
    static JTextField homephonetext;
    public void add(Component c, GridBagConstraints constraints, int x, int y, int w, int h){
        constraints.gridx=x;
        constraints.gridy=y;
        constraints.gridwidth=w;
        constraints.gridheight=h;
        pane.add(c,constraints);
    }
    classmatecommunication(){
        pane=new JPanel();
        GridBagLayout lay=new GridBagLayout();
        pane.setLayout(lay);
        GridBagConstraints constraints=new GridBagConstraints();
        constraints.fill=GridBagConstraints.NONE;
        constraints.weightx=5;
        constraints.weighty=3;
        name=new JLabel("姓名");
        nameinput=new JComboBox();
        title=new JLabel("家庭电话号码");
        title1=new JLabel("个人移动电话号码");
        homephonetext=new JTextField(20);
        moteltext=new JTextField(20);
        closebutton =new JButton("关闭此窗口");
        add(title,constraints,1,0,3,1);
        add(homephonetext,constraints,1,1,3,1);
        add(title1,constraints,1,2,3,1);
        add(moteltext,constraints,1,3,3,1);
        add(closebutton,constraints,2,4,3,1);
        add(title,constraints,1,1,3,1);
        add(homephonetext,constraints,1,2,3,1);
        add(title1,constraints,1,3,3,1);
        add(moteltext,constraints,1,4,3,1);
        add(closebutton,constraints,2,5,3,1);
        add(name,constraints,1,0,1,1);
        add(nameinput,constraints,2,0,1,1);
        Vector vec=store.getclassmate(con,sql);
        for(int i=0;i<vec.size();i++){
            classmate one =(classmate)vec.get(i);
            String nameselect=one.getname();
            nameinput.addItem(nameselect);
        }
        String namestring=(String)nameinput.getSelectedItem();
        classmate p=store.getobject(con,namestring);
        String inputtel=p.getcontact();
        String inputmotel=p.gethomephone();
        homephonetext.setText(inputtel);
        moteltext.setText(inputtel);
        nameinput.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String namestring=(String)nameinput.getSelectedItem();
                classmate p=store.getobject(con,namestring);
                String inputtel=p.getcontact();
                String inputmotel=p.gethomephone();
                homephonetext.setText(inputtel);
                moteltext.setText(inputtel);
            }
        });
        closebutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pane.setVisible(false);
            }
        });
        pane.setVisible(true);
    }
}
