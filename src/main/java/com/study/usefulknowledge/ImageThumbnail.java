package com.study.usefulknowledge;

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
/**@title 20个java常用代码
 *@desc 生成图片缩略图(七)
 * @create 20130708
 * @author usefulknowledge
 * */
public class ImageThumbnail {
public static void main(String[] args) {
	String filename="src/love.png";
	String outFilename="src/love_thumb.png";
	try {
		createThumbnail(filename, 390	, 225, 100, outFilename);
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	@SuppressWarnings("unused")
	private static void createThumbnail(String filename, int thumbWidth,
			int thumbHeight, int quality, String outFilename)
			throws InterruptedException,

			FileNotFoundException, IOException {

		// 加载图片文件

		Image image =Toolkit.getDefaultToolkit().getImage(filename);
		MediaTracker mediaTracker = new MediaTracker(new Container());
		mediaTracker.addImage(image,0);
		//强制加载图像
		mediaTracker.waitForID(0);
		// 测试图片是否加载成功:
		System.out.println(mediaTracker.isErrorAny());

		// 通过参数判断缩略图大小

		double thumbRatio = (double) thumbWidth / (double) thumbHeight;

		int imageWidth = image.getWidth(null);
		int imageHeight= image.getHeight(null);

		double imageRatio = (double) imageWidth / (double) imageHeight;
		//缩小
		if (thumbRatio < imageRatio){
			thumbHeight= (int) (thumbWidth / imageRatio);
		} else {//放大
			thumbWidth =(int) (thumbHeight * imageRatio);
		}

		// 创建缩略图对象 并设置quality

		BufferedImage thumbImage = new

		BufferedImage(thumbWidth, thumbHeight,

		BufferedImage.TYPE_INT_RGB);

		Graphics2D graphics2D =

		thumbImage.createGraphics();

		graphics2D.setRenderingHint

		(RenderingHints.KEY_INTERPOLATION,

		RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		graphics2D.drawImage(image, 0, 0,

		thumbWidth, thumbHeight, null);
		// 输出缩略图
		BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream(outFilename));
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		JPEGEncodeParam param =		encoder.getDefaultJPEGEncodeParam(thumbImage);

		quality = Math.max(0, Math.min

		(quality, 100));
		param.setQuality((float) quality / 100.0f, false);
		encoder.setJPEGEncodeParam(param);
		encoder.encode(thumbImage);
		out.close();

	
	}

}
