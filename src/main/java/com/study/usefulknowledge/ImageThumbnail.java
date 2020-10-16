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
/**@title 20��java���ô���
 *@desc ����ͼƬ����ͼ(��)
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

		// ����ͼƬ�ļ�

		Image image =Toolkit.getDefaultToolkit().getImage(filename);
		MediaTracker mediaTracker = new MediaTracker(new Container());
		mediaTracker.addImage(image,0);
		//ǿ�Ƽ���ͼ��
		mediaTracker.waitForID(0);
		// ����ͼƬ�Ƿ���سɹ�:
		System.out.println(mediaTracker.isErrorAny());

		// ͨ�������ж�����ͼ��С

		double thumbRatio = (double) thumbWidth / (double) thumbHeight;

		int imageWidth = image.getWidth(null);
		int imageHeight= image.getHeight(null);

		double imageRatio = (double) imageWidth / (double) imageHeight;
		//��С
		if (thumbRatio < imageRatio){
			thumbHeight= (int) (thumbWidth / imageRatio);
		} else {//�Ŵ�
			thumbWidth =(int) (thumbHeight * imageRatio);
		}

		// ��������ͼ���� ������quality

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
		// �������ͼ
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
