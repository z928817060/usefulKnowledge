package com.study.usefulknowledge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
/**@title 20��java���ô���
 *@desc socketͨ��(ʮ��)
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
			System.out.println("���ӷ���˵ȴ���..");

			Socket socket = new Socket("localhost", 10000);
			
			System.out.println("���ӷ���˳ɹ�");
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			printWriter = new PrintWriter(outputStream, true);
			reader = new BufferedReader(new InputStreamReader(inputStream,
					"gbk"));
			while (true) {
				printWriter.println("���ǿͻ���");
				printWriter.flush();
				String str = reader.readLine();
				System.out.println("�յ��������Ӧ:" + str);
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

