package com.study.usefulknowledge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
/**@title 20��java���ô���
 *@desc socketͨ��(ʮ��)
 * @create 20130708
 * @author usefulknowledge
 * */
public class ServerConn {
	InputStream inputStream = null;
	OutputStream outputStream = null;
	BufferedReader reader = null;
	PrintWriter printWriter = null;
public static void main(String[] args) {
	new ServerConn();
}
	public ServerConn() {

		try {
			System.out.println("������׼������");
			System.out.println(System.getProperties().getProperty(
					"http.proxyHost"));
			System.out.println(1);
			ServerSocket serverSocket = new ServerSocket(10000);
			Socket socket = serverSocket.accept();
			System.out.println("��������ʼ����");
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			reader = new BufferedReader(new InputStreamReader(inputStream,
					"gbk"));
			printWriter = new PrintWriter(outputStream, true);
			while (true) {

				String str = reader.readLine();
				System.out.println("�������յ���Ϣ:" + str);
				printWriter.println("���,�ͻ���");
				printWriter.flush();
				break;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// �ر�������

		}

	}
}
