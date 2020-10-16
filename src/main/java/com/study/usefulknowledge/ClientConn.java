package com.study.usefulknowledge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
/**@title 20个java常用代码
 *@desc socket通信(十四)
 * @create 20130708
 * @author usefulknowledge
 * */
public class ClientConn {
public static void main(String[] args) {
	new ClientConn();
}

	InputStream inputStream = null;
	OutputStream outputStream = null;
	BufferedReader reader = null;
	PrintWriter printWriter = null;

	public ClientConn() {
		try {
			System.out.println("连接服务端等待中..");

			Socket socket = new Socket("localhost", 10000);
			
			System.out.println("连接服务端成功");
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			printWriter = new PrintWriter(outputStream, true);
			reader = new BufferedReader(new InputStreamReader(inputStream,
					"gbk"));
			while (true) {
				printWriter.println("我是客户端");
				printWriter.flush();
				String str = reader.readLine();
				System.out.println("收到服务端响应:" + str);
				break;
			}

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}

