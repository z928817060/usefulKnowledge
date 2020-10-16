package com.study.usefulknowledge.UI.通讯系统;

import javax.swing.*;
import java.awt.*;

public class version extends JPanel {
    private static final long serialVersionUID=1L;
    static final int WIDTH=200;
    static final int HEIGHT=100;
    JFrame versionframe;
    public void add(Component c, GridBagConstraints constraints, int x, int y, int w, int h){
        constraints.gridx=x;
        constraints.gridy=y;
        constraints.gridwidth=w;
        constraints.gridheight=h;
        add(c,constraints);
    }
    version(){
        versionframe=new JFrame("通讯录版本信息");
        versionframe.setContentPane(this);
        int screemWidth=(int)this.getToolkit().getScreenSize().getWidth();//screemwidth
        int screemHeight=(int)this.getToolkit().getScreenSize().getHeight();
        versionframe.setLocation((screemWidth-WIDTH)/2,(screemHeight-HEIGHT)/2);//设置窗口位置，离左边(screemWidth-width)/2，上边像素
        versionframe.setResizable(false);  //设置窗口大小不可调节
        versionframe.setSize(WIDTH,HEIGHT);//窗口大小，像素
        versionframe.setVisible(true);//显示或隐藏窗口
        GridBagLayout layout=new GridBagLayout();
        setLayout(layout);
        GridBagConstraints constraints=new GridBagConstraints();
        JLabel label1=new JLabel("通讯录信息系统版本信息");
        JLabel label2=new JLabel("软件版本1.0");
        JLabel label3=new JLabel("作者zh");
        add(label1,constraints,1,0,2,1);
        add(label2,constraints,1,1,2,1);
        add(label3,constraints,2,2,1,1);
    }
}
