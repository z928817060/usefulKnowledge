package com.study.usefulknowledge;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
/**@title 20个java常用代码
 *@desc 代理设置(九)
 * @create 20130708
 * @author usefulknowledge
 * */
public class HttpProxy {

	public static void main(String[] args) {
		String proxyHost = "";
		String proxyPort = "";
		String proxyUser = "";
		String proxyPassword = "";
		// 全局设置
		// setHttpProxy(proxyHost,proxyPort,proxyUser,proxyPassword);
		setHttpProxy2();// 局部设置
	}

	private static void setHttpProxy2() {
		URL url=null;
		Proxy proxy=null;
		String hostname="118.174.144.30";
		int port=80;
		String charset="utf-8";
		StringBuffer tmp=new StringBuffer();
		try {
			 url=new URL("http://www.cnblogs.com");
			 proxy=new Proxy(Proxy.Type.HTTP,new InetSocketAddress(hostname,port));
			 HttpURLConnection uc= (HttpURLConnection) url.openConnection(proxy);
			uc.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");

			uc.setRequestProperty("Accept","*/*");
			uc.setRequestMethod("GET");
			uc.setRequestProperty("Charset", charset);
			uc.setUseCaches(false);
			uc.setRequestProperty("Content-Type", "application/octet-stream");
			//uc.addRequestProperty("Accept-Encoding", "gzip");

			uc.setDoInput(true);

			uc.connect();

			String line = "";

			if( uc.getResponseCode() == HttpURLConnection.HTTP_OK){

			   BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(),charset));

			   while ((line = in.readLine()) != null) {

			       tmp.append(line).append("\n");

			   }
			}else{
				 tmp.append("代理设置不成功或则远程地址无效");
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(tmp);
	}

	private static void setHttpProxy(String proxyHost, String proxyPort,
			String proxyUser, String proxyPassword) {

		System.getProperties().put("http.proxyHost", proxyHost);

		System.getProperties().put("http.proxyPort", proxyPort);
		System.getProperties().put("http.proxyUser", proxyUser);
		System.getProperties().put("http.proxyPassword", proxyPassword);

	}

}
