package com.study.usefulknowledge.UI.通讯系统;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainframe {

    private static final long serialVersionUID=1L;
    static final int WIDTH=600;
    static final int HEIGHT=400;
    JPopupMenu pop;
    JMenuItem item2;
    JMenuItem item1;
    JFrame f;
    JToolBar bar;
    JPanel p1;
    JPanel p2;
    JPanel p3;
    tabpane tab1;
    tabpane tab2;
    tabpane tab3;
    JButton closebutton1;
    JButton closebutton2;
    JButton closebutton3;
    JButton addbutton1;
    JButton addbutton2;
    JButton addbutton3;
    JButton delbutton1;
    JButton delbutton2;
    JButton delbutton3;
    JButton updatebutton1=new JButton();
    JButton updatebutton2=new JButton();
    JButton updatebutton3=new JButton();
    //为了更快的显示点击出的窗口，可以将显示缓慢的窗口放到桌面，设置为不显示，可以使用个cardlayout
    JPanel p;//cardpanel
    JPanel panel1=new JPanel();//同学基本信息系统
    JPanel panel2=new JPanel();//同学联系方式系统
    JPanel panel3=new JPanel();//同事基本信息系统
    JPanel panel4=new JPanel();//同事联系方式系统
    JPanel panel5=new JPanel();//朋友基本信息系统
    JPanel panel6=new JPanel();//朋友联系方式系统
    public mainframe(){
        f=new JFrame("我的通讯录系统主页面");
        JMenuBar menubar1=new JMenuBar();
        p=new JPanel();
        f.setContentPane(p);
        f.setJMenuBar(menubar1);
        //创建菜单系统
        JMenu menu1=new JMenu("同学通讯系统");
        JMenu menu2=new JMenu("同事通讯系统");
        JMenu menu3=new JMenu("朋友通讯系统");
        JMenu menu4=new JMenu("查询系统");
        JMenu menu5=new JMenu("帮助系统");
        menubar1.add(menu1);
        menubar1.add(menu2);
        menubar1.add(menu3);
        menubar1.add(menu4);
        menubar1.add(menu5);
        item1=new JMenuItem("同学基本信息系统");
        item2=new JMenuItem("同学联系方式系统");
        JMenuItem item3=new JMenuItem("退出通讯录系统");
        JMenuItem item4=new JMenuItem("同事基本信息系统");
        JMenuItem item5=new JMenuItem("同事联系方式系统");
        JMenuItem item6=new JMenuItem("朋友基本信息系统");
        JMenuItem item7=new JMenuItem("朋友联系方式系统");
        JMenuItem item8=new JMenuItem("同学查询系统");
        JMenuItem item9=new JMenuItem("同事查询系统");
        JMenuItem item10=new JMenuItem("朋友查询系统");
        JMenuItem item11=new JMenuItem("版本信息");
        JMenuItem item12=new JMenuItem("帮助信息");
        menu1.add(item1);
        menu1.addSeparator();
        menu1.add(item2);
        menu1.addSeparator();
        menu1.add(item3);
        menu2.add(item4);
        menu2.addSeparator();
        menu2.add(item5);
        menu3.add(item6);
        menu3.addSeparator();
        menu3.add(item7);
        menu4.add(item8);
        menu4.addSeparator();
        menu4.add(item9);
        menu4.addSeparator();
        menu4.add(item10);
        menu5.add(item11);
        menu5.addSeparator();
        menu5.add(item12);
        JButton button1=new JButton("同学查询系统");
        JButton button2=new JButton("同事查询系统");
        JButton button3=new JButton("朋友查询系统");
        closebutton1=new JButton("关闭");
        closebutton2=new JButton("关闭");
        closebutton3=new JButton("关闭");
        addbutton1=new JButton("添加同学信息数据");
        addbutton2=new JButton("添加同事信息数据");
        addbutton3=new JButton("添加朋友信息数据");
        delbutton1=new JButton("删除同学信息数据");
        delbutton2=new JButton("删除同事信息数据");
        delbutton3=new JButton("删除朋友信息数据");
        updatebutton1=new JButton("更新同学信息数据");
        updatebutton2=new JButton("更新同事信息数据");
        updatebutton3=new JButton("更新朋友信息数据");
        //添加到容器
        p1=new JPanel();
        p2=new JPanel();
        p3=new JPanel();
        p1.setLayout(new FlowLayout());
        p1.add(closebutton1);
        p1.add(addbutton1);
        p1.add(delbutton1);
        p1.add(updatebutton1);
        p2.setLayout(new FlowLayout());
        p2.add(closebutton2);
        p2.add(addbutton2);
        p2.add(delbutton2);
        p2.add(updatebutton2);
        p3.setLayout(new FlowLayout());
        p3.add(closebutton3);
        p3.add(addbutton3);
        p3.add(delbutton3);
        p3.add(updatebutton3);
        bar=new JToolBar();
        bar.add(button1);
        bar.add(button2);
        bar.add(button3);
        CardLayout card=new CardLayout();
        p.setLayout(card);
        p.add(panel1,"panel1");
        p.add(panel2,"panel2");
        p.add(panel3,"panel3");
        p.add(panel4,"panel4");
        p.add(panel5,"panel5");
        p.add(panel6,"panel6");

        //panel1设置布局
        BorderLayout bord=new BorderLayout();
        panel1.setLayout(bord);
        panel1.add("North",bar);   //先加空的tab1（JTabbedPane tab1）
        tab1 = new tabpane();
        panel1.add("Center", tab1);
        tab1.panel1.add(new classinfo());
        panel1.add("South", p1);
        //panel2设置
        bord=new BorderLayout();
        panel2.setLayout(bord);
        panel2.add("North",bar);
        classmatecommunication cc=new classmatecommunication();
        panel2.add("Center",cc.pane);
        //panel3设置
        bord=new BorderLayout();
        panel3.setLayout(bord);
        tab2=new tabpane();
        panel3.add("Center",tab2);
        tab2.panel1.add(new companyinfo());
        panel3.add("South",p2);
        //panel4设置
        bord=new BorderLayout();
        panel4.setLayout(bord);
        companycommunication cc1=new companycommunication();
        panel4.add("Center",cc1.pane);
        //panel5设置
        bord=new BorderLayout();
        panel5.setLayout(bord);
        tab3=new tabpane();
        panel5.add("Center",tab3);
        tab3.panel1.add(new friendinfo());
        panel5.add("South",p3);
        //panel6设置
        bord=new BorderLayout();
        panel6.setLayout(bord);
        friendcommunication fc=new friendcommunication();
        panel6.add("Center",fc.pane);

        int screemWidth=(int)f.getToolkit().getScreenSize().getWidth();//screemwidth
        int screemHeight=(int)f.getToolkit().getScreenSize().getHeight();
        f.setLocation((screemWidth-WIDTH)/2,(screemHeight-HEIGHT)/2);//设置窗口位置，离左边(screemWidth-width)/2，上边像素
//        this.setLocationRelativeTo(null);//设置为位置不可调
//        this.setResizable(false);  //设置窗口大小不可调节
        f.setResizable(true);  //设置窗口大小不可调节
        f.setSize(WIDTH,HEIGHT);//窗口大小，像素
        f.setVisible(true);//显示或隐藏窗口

        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(p,"panel1");
            }
        });
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(p,"panel2");
            }
        });
        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i=JOptionPane.showConfirmDialog(null,"是否真的需要退出系统","退出系统对话框",JOptionPane.YES_NO_CANCEL_OPTION);
                if(i==0){
                    f.dispose();
                }
            }
        });
        item4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(p,"panel3");
            }
        });
        item5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(p,"panel4");
            }
        });
        item6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(p,"panel5");
            }
        });
        item7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(p,"panel6");
            }
        });
        item8.addActionListener(new ActionListener() {    //查询未写
            @Override
            public void actionPerformed(ActionEvent e) {
//                classmatefind cf=new classmatefind();
//                p.add("Center",cf.pane);
//                cf.pane.setVisible(true);
            }
        });
        item9.addActionListener(new ActionListener() {    //查询未写
            @Override
            public void actionPerformed(ActionEvent e) {
//                companyfind cf1=new companyfind();
//                p.add("Center",cf1.pane);
//                cf1.pane.setVisible(true);
            }
        });
        item10.addActionListener(new ActionListener() {    //查询未写
            @Override
            public void actionPerformed(ActionEvent e) {
//                friendfind ff=new friendfind();
//                p.add("Center",ff.pane);
//                ff.pane.setVisible(true);
            }
        });
        item11.addActionListener(new ActionListener() {    //查询未写
            @Override
            public void actionPerformed(ActionEvent e) {
                new version();
            }
        });
        item12.addActionListener(new ActionListener() {    //查询未写
            @Override
            public void actionPerformed(ActionEvent e) {
                new help();
            }
        });
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                classmatefind cf=new classmatefind();
//                p.add("Center",cf.pane);
//                cf.pane.setVisible(true);
            }
        });
        button2.addActionListener(new ActionListener() {    //查询未写
            @Override
            public void actionPerformed(ActionEvent e) {
//                companyfind cf1=new companyfind();
//                p.add("Center",cf1.pane);
//                cf1.pane.setVisible(true);
            }
        });
        button3.addActionListener(new ActionListener() {    //查询未写
            @Override
            public void actionPerformed(ActionEvent e) {
//                friendfind ff=new friendfind();
//                p.add("Center",ff.pane);
//                ff.pane.setVisible(true);
            }
        });
        closebutton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab1.setVisible(true);
                p1.setVisible(true);
            }
        });
        closebutton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab2.setVisible(true);
                p2.setVisible(true);
            }
        });
        closebutton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tab3.setVisible(true);
                p3.setVisible(true);
            }
        });
        addbutton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new addclassmate();
            }
        });
        addbutton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new addcompany();
            }
        });
        addbutton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new addfriend();
            }
        });
        delbutton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i=JOptionPane.showConfirmDialog(null,"是否真的删除此项","退出确认对话框",JOptionPane.YES_NO_CANCEL_OPTION);
                if(i==0){
                    new delclassmate();
                }
            }
        });
        delbutton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i=JOptionPane.showConfirmDialog(null,"是否真的删除此项","退出确认对话框",JOptionPane.YES_NO_CANCEL_OPTION);
                if(i==0){
                    new delcompany();
                }
            }
        });
        delbutton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i=JOptionPane.showConfirmDialog(null,"是否真的删除此项","退出确认对话框",JOptionPane.YES_NO_CANCEL_OPTION);
                if(i==0){
                    new delfriend();
                }
            }
        });
        updatebutton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i=JOptionPane.showConfirmDialog(null,"是否真的更新此项","退出确认对话框",JOptionPane.YES_NO_CANCEL_OPTION);
                if(i==0){
//                    new updateclassmate();
                }
            }
        });
        updatebutton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i=JOptionPane.showConfirmDialog(null,"是否真的更新此项","退出确认对话框",JOptionPane.YES_NO_CANCEL_OPTION);
                if(i==0){
//                    new updatecompany();
                }
            }
        });
        updatebutton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i=JOptionPane.showConfirmDialog(null,"是否真的更新此项","退出确认对话框",JOptionPane.YES_NO_CANCEL_OPTION);
                if(i==0){
//                    new updatefriend();
                }
            }
        });

    }
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new mainframe();
            }
        });
    }
}
