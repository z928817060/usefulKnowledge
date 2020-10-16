package com.study.usefulknowledge.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * 渐变色GradientPaint，圆形渐变色RadialGradientPaint
 * 注：都要重写panel的画图方法
 */
class FillGradientPanel extends JPanel {
    // 创建内部面板类
    public void paint(Graphics g) {
// 重写paint()方法
        Graphics2D g2 = (Graphics2D) g;
// 获得Graphics2D对象
        Rectangle2D.Float rect = new Rectangle2D.Float
                (20, 20, 280, 140);             // 创建矩形对象
        GradientPaint paint = new GradientPaint(20,20,
                Color.BLUE,280,20,Color.RED,true);      // 创建循环渐变的GradientPaint对象
        g2.setPaint(paint);
// 设置渐变
        g2.fill(rect);
// 绘制矩形
    }
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame jFrame=new JFrame("test");
                jFrame.setContentPane(new FillGradientPanel());
                jFrame.setVisible(true);
                jFrame.pack();
                jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                //圆形、环渐变色
                SwingWorker swingWorker=new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        System.out.println("qaa");
                        JFrame f = new JFrame("试试");
                        MyFrame mf = new MyFrame();
                        f.add(mf);
                        f.setLocationRelativeTo(null);
                        f.setSize(200, 200);
                        f.setVisible(true);
                        f.setDefaultCloseOperation(3);
                        return null;
                    }
                };
                swingWorker.execute();
            }
        });
    }
    static class MyFrame extends JPanel{
        public void paintComponent(Graphics g){
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            RadialGradientPaint p = new RadialGradientPaint(new Point2D.Double(getWidth() / 2.0,getHeight() / 2.0),
                    getWidth() / 2.4f,
                    new float[] { 0.0f, 1f },
                    new Color[] { new Color(255,255,255,180),
                            new Color(0,0,0,255) });
            g2.setPaint(p);
            g2.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
        }
    }
}