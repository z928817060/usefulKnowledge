package com.study.usefulknowledge.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.*;

class DrawingExample implements ActionListener {
    JFrame frame;
    MyButton  button;
    MyPanel panel;
    int tag = 1;

    public static void main(String args[]) {
        DrawingExample de = new DrawingExample();
        de.go();
    }

    public void go() {
        frame = new JFrame("Drawing  Example");
        button = new MyButton("Draw");
        button.addActionListener(this);
        frame.getContentPane().add(button,"South");

        panel = new MyPanel();
        frame.getContentPane().add(panel,"Center");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360,200);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
//按钮上的文本在Draw与Clear间切换
        if (tag==0) {
            tag = 1;
            button.setText("Draw");
        }else {
            tag = 0;
            button.setText("Clear");
        }
        panel.repaint();//重绘panel
    }

    //自定义的button
    class MyButton extends JButton {
        MyButton(String text) {
            super(text);
        }

        protected void paintComponent(Graphics g){ //什么时候被调用?被谁调用?
            super.paintComponent(g);
            g.setColor(Color.red);
            int width = getWidth();
            int height = getHeight();
            g.drawOval(4, 4, width-8, height-8);//绘制椭圆
        }
    }
    //自定义的panel
    class MyPanel extends JPanel {
        protected void paintComponent(Graphics g){ //什么时候被调用?被谁调用?
            super.paintComponent(g);
            if (tag==0) {
                g.setColor(Color.blue); //设置颜色
                g.drawLine(40,25,30,50); //绘制直线
                g.setColor(Color.green);
                g.drawRect(100,50,100,46);//绘制矩形
                g.setColor(Color.red);
                g.drawRoundRect(73,32,56,37,10,16); //绘制圆角矩形
                g.setColor(Color.yellow);
                g.fillOval(180,60,60,45);  //绘制填充椭圆
                g.setColor(Color.pink);
                g.fillArc(250,32,90,60,15,30); //绘制填充圆弧

                //设置图形上下文环境
                Stroke stroke=new BasicStroke(10,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_BEVEL);

            }
        }
    }
    class MyPanel2d extends JPanel {
        protected void paintComponent(Graphics g){ //什么时候被调用?被谁调用?
            super.paintComponent(g);
            if (tag==0) {
                Graphics2D g2d=(Graphics2D) g;
                //获取之前的各属性，以便后面的属性不被新设定的属性影响
                Stroke oldstroke=g2d.getStroke();
                Paint oldpaint=g2d.getPaint();
                AffineTransform old=g2d.getTransform();
//设置图形上下文环境
                //设置 stroke属性控制线条的 BasicStroke(float w,int cap, int join)
                // 1宽度float w
                // 2 int capcap是端点：CAP_BUTT(无修饰)，CAP_ROUND(半圆形末端)，CAP_SQUARE(方形末端，默认值)
                // 3 线段连接方式JOIN_BEVEL(无修饰),JOIN_MTTER(尖形末端，默认值),JOIN_ROUND(圆形末端)
                Stroke stroke=new BasicStroke(10,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_BEVEL);
                g2d.setStroke(stroke);
                //设置 paint属性控制填充效果GradientPaint(float x1,float y1,Color c1,float x2,float y2,Color c2,Boolean cyclic)
                //起始点坐标和末尾点坐标，如果希望渐变到终点又是起点的颜色，应将cyclic设置为true
                Paint paint=new GradientPaint(100,100,Color.RED,200,100,Color.BLUE,true);
                g2d.setPaint(paint);
                //设置 transform 属性用来实现常用的图形平移、缩放和斜切等变换操作
                //1.transelate(double dx,double dy)：将图形在x轴方向平移dx像素。
                //2.scale(double sx,double sy)：图形在x轴方向缩放sx倍，纵向缩放sy倍。
                //3.rotate(double arc,double x, double y)：图形以点(x,y)为轴点，旋转arc弧度。
                AffineTransform trans = new AffineTransform();
                trans.rotate(50.0*3.1415927/180.0,90,80);
                g2d.setTransform(trans);
                //设置 重叠区域的透明效果
                // AlphaComposite.getInstance(int rule, float alpha)
                //Alpha值的范围为0.0f(完全透明)-0.1f(完全不透明)
                AlphaComposite alphaComposite=AlphaComposite.getInstance(1, (float) 0.05);
                g2d.setComposite(alphaComposite);
//设置图形形状
                //线段
                Line2D line = new Line2D.Double(2,3,200,300);//声明并创建线段对象//起点是(2，3)，终点是(200，300)
                //矩形
                Rectangle2D rect = new Rectangle2D.Double(20,30,80,40);//声明并创建矩形对象，矩形的左上角是(20，30)，宽是300，高是40
                //圆角矩形
                RoundRectangle2D rectRound = new RoundRectangle2D.Double(20,30,130,100,18,15);//左上角是(20，30)，宽是130，高是100，圆角的长轴是18，短轴是15。
                //椭圆
                Ellipse2D ellipse = new Ellipse2D.Double(20,30,100,50);//左上角 (20，30)，宽是100，高是50
                //圆弧
                Arc2D arc1 = new Arc2D.Double(8,30,85,60,5,90,Arc2D.OPEN);
                        //外接矩形的左上角(10，30)，宽85，高60，起始角是5度，终止角是90度，Arc2D.OPEN、Arc2D.CHORD、Arc2D.PIE分别表示圆弧是开弧、弓弧和饼弧
                //二次曲线
                QuadCurve2D curve1 = new QuadCurve2D.Double(20,10,90,65,55,115);
                        //一条二次曲线需要三个点确定：始点、控制点和终点。控制点不知道
                //三次曲线
                CubicCurve2D curve2 = new CubicCurve2D.Double(12,30,50,75,15,15,115,93);
                        //方法Double()中的8个参数分别是三次曲线的始点、两个控制点和终点
//画图，直接draw
                g2d.draw(curve2);







            }
        }
    }
}