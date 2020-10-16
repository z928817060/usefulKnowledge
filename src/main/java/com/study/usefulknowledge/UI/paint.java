package com.study.usefulknowledge.UI;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Administrator on 2018/5/11.
 */
public class paint {

    public static void main(String[] args) {
        int step = 10;
        int w = 100*step;
        int h = 50 * step;
        int pix[] = new int[w*h];
        int index = 0;
        int color = (255<<24)|(255<<16)|(255<<8)|96;//亮度，red,green,blue
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x=x+step) {
                //功能：格点数据占多个像素点显示，step=1时，表示占单个像素点显示
                //数据部分：数据格点[y/step][x/step],行的最小单位y/step  ,列的最小单位x/step,显示的图像像素点总个数x，y
                if(y/step>= 50 || x/step>=100)continue; //防止由于非整除时，数组越界
                color = (150<<24)|(255<<16)|(0<<8)|150;
                color = ((y/step)%2==1)? (150<<24)|(255<<16)|(0<<8)|150 :  (150<<24)|(255<<16)|(0<<8)|0; //按照行变
                color = ((y/step)%2==1)? (150<<24)|(255<<16)|(0<<8)|150 :  (150<<24)|(255<<16)|(0<<8)|0; //按照列变

                for (int i = 0; i < step; i++) {
                    pix[index++] = color;//这里的样色排布：左上到右，每行读取
                }
            }

        }
        //给成Image
        Image img = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(w,h,pix,0,w));
        //给成BufferedImage
        BufferedImage bufferedImage = new BufferedImage(w,h,BufferedImage.TYPE_4BYTE_ABGR);
        bufferedImage.createGraphics().drawImage(img,0,0,null);
        //保存
        Iterator<ImageWriter> it = ImageIO.getImageWritersByFormatName("png");
        ImageWriter writer = it.next();
        File f = new File("C:\\Users\\Administrator\\Desktop\\2.jpg");
        ImageOutputStream ios = null;
        try {
            ios = ImageIO.createImageOutputStream(f);
            writer.setOutput(ios);
            writer.write(bufferedImage);
            bufferedImage.flush();
            ios.flush();
            ios.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        JFrame jFrame = new JFrame();   //在panel中显示，Jpanel需要Image
//        MyPanel m = new MyPanel(img);
////        m.setVisible(true);
//        int  WIDTH = 1000;
//        int  HEIGHT = 500;
//        Toolkit kit=Toolkit.getDefaultToolkit();
//        Dimension screenSize=kit.getScreenSize();  //系统对象获取工具
//        int width=screenSize.width;
//        int height=screenSize.height;
//        int x=(width-WIDTH)/2;
//        int y=(height-HEIGHT)/2;
//        jFrame.setLocation(x,y);  //设置位置
//        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭
//        jFrame.setContentPane(m);
//        jFrame.setSize(WIDTH,HEIGHT);
//        jFrame.setVisible(true);
    }
}

class MyPanel extends JPanel{
    public MyPanel(Image image  ) {
        this.image = image;
    }
    Image image=null;
    @Override
    public void paintComponent(Graphics g ){
        try {
            g.drawImage(image, 0, 0, null);
            image = ImageIO.read(new File("C:\\Users\\Administrator\\Desktop\\1.jpg"));
            g.drawImage(image, 50, 50, null);//位置
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}