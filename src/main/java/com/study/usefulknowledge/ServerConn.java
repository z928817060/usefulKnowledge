package com.study.usefulknowledge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
/**@title 20个java常用代码
 *@desc socket通信(十四)
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
			System.out.println("服务器准备监听");
			System.out.println(System.getProperties().getProperty(
					"http.proxyHost"));
			System.out.println(1);
			ServerSocket serverSocket = new ServerSocket(10000);
			Socket socket = serverSocket.accept();
			System.out.println("服务器开始监听");
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			reader = new BufferedReader(new InputStreamReader(inputStream,
					"gbk"));
			printWriter = new PrintWriter(outputStream, true);
			while (true) {

				String str = reader.readLine();
				System.out.println("服务器收到信息:" + str);
				printWriter.println("你好,客户端");
				printWriter.flush();
				break;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭流操作

		}

	}
}
