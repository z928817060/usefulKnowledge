package com.study.usefulknowledge;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
/**@title 屏幕截取
 *@desc ��ȡ����(ʮ)
 * @create 20130708
 * @author usefulknowledge
 * */
public class CaptureScreen截屏 {
	
	public static void main(String[] args) {
		try {
			captureScreen("C:\\Users\\Administrator\\Desktop\\x.png");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void captureScreen(String fileName) throws Exception {


		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle screenRectangle = new Rectangle(screenSize);
		Robot robot = new Robot();
		BufferedImage image = robot.createScreenCapture(screenRectangle);
		ImageIO.write(image, "png", new File(fileName));


		}

}

