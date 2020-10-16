package com.study.usefulknowledge.UI.通讯系统;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//登陆系统，设计成一个继承容器的类
class loggins extends JPanel {
    private static final long serialVersionUID=1L;
    static final int WIDTH=300;
    static final int HEIGHT=150;
    JFrame loginframe;
    public void add(Component c,GridBagConstraints constraints,int x,int y,int w,int h){
        constraints.gridx=x;
        constraints.gridy=y;
        constraints.gridwidth=w;
        constraints.gridheight=h;
        add(c,constraints);
    }
    loggins(){
        loginframe=new JFrame("我的通讯录系统");
        loginframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout lay=new GridBagLayout();
        setLayout(lay);
        loginframe.add(this,BorderLayout.WEST);
        loginframe.setSize(WIDTH,HEIGHT);
        Toolkit kit=Toolkit.getDefaultToolkit();
        Dimension screenSize=kit.getScreenSize();  //系统对象获取工具
        int width=screenSize.width;
        int height=screenSize.height;
        int x=(width-WIDTH)/2;
        int y=(height-HEIGHT)/2;
        loginframe.setLocation(x,y);
        JButton ok=new JButton("登录");
        JButton cancel=new JButton("放弃");
        JLabel title=new JLabel("通讯簿系统登陆窗口");
        JLabel name=new JLabel("用户名");
        JLabel password=new JLabel("密 码");
        final JTextField nameinput=new JTextField(15);
        final JTextField passwordinput=new JTextField(15);
        GridBagConstraints constraints=new GridBagConstraints();
        constraints.fill=GridBagConstraints.NONE;
        constraints.anchor=GridBagConstraints.EAST;
        constraints.weightx=4;
        constraints.weighty=4;
        add(title,constraints,2,0,2,1);
        add(name,constraints,1,1,2,1);
        add(password,constraints,1,2,2,1);
        add(nameinput,constraints,3,1,2,1);
        add(passwordinput,constraints,3,2,2,1);
        add(ok,constraints,1,3,1,1);
        add(cancel,constraints,3,3,1,1);
        loginframe.setResizable(false);
        loginframe.setVisible(true);
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nametext=nameinput.getText();
                String passwordtext=passwordinput.getText();
                String str=new String(passwordtext);
                boolean x=(nametext.equals("starsong"));
                boolean y=(str.equals("750720"));
                boolean z=(x&&y);
                if(z==true){
                    new mainframe();
                    loginframe.dispose();
                }else if(z==false){
                    nameinput.setText("");
                    passwordinput.setText("");
                }
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginframe.dispose();
            }
        });
    }
}

public class loggin{
    public static void main(String[] args){
        new loggin();
    }
}
