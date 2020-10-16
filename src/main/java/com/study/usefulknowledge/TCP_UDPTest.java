package com.study.usefulknowledge;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;

public class TCP_UDPTest {
    public void TCP_UDPTest(){
    };
    public static void main(String[] args){
        TCP tcp=new TCP();
//        tcp.server();
        tcp.client();
    }
}

/**
 * TCP
 * 主要思想：Socket、ServerSocket连接，设置ip和端口，利用getInputStream和getOutputStream获得流，然后利用流去。。。
 * 主要类Socket  ServerSocket
 */
class TCP{
    @Test
    public void TCP(){
    }
    //客户端
    public void client(){
        Socket socket=null;
        OutputStream os=null;
        InputStream is=null;
        try {
            //创建socket对象，指明IP和端口
            System.out.print("aaa");
            socket=new Socket("localhost",9090);//127.0.0.1InetAddress.getByName("192.6.1.137")
            //给出输出流，用来发给服务器端的
            os=socket.getOutputStream();
            os.write("我是客户端".getBytes());
            //告诉服务器端发送完毕，shutdown
            socket.shutdownOutput();
            //给出输入流，用来接收服务器端的
            is=socket.getInputStream();
            byte[] b=new byte[20];
            int len;
            while ((len=is.read(b))!=-1) {
                String str = new String(b, 0, len);
                System.out.print(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is!=null)
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if(os!=null)
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if(socket!=null)
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
    //服务器端
    @Test
    public void server(){
        ServerSocket ss=null;
        Socket s=null;
        InputStream is=null;
        OutputStream os=null;
        try {
            //对接端口
            ss=new ServerSocket(9090);
            //接收
            s=ss.accept();
            //流操作，接收客户端的
            is=s.getInputStream();
            byte[] b =new byte[20];
            int len ;
            while ((len=is.read(b))!=-1){
                String str =new String(b,0,len);
                System.out.print(str);
            }
            //发给客户端的
            os=s.getOutputStream();
            os.write("from server".getBytes());
            s.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is!=null)
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if(s!=null)
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if(ss!=null)
                try {
                    ss.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if(os!=null)
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
}
/**
 * UDP
 * 主要思想：创建DatagramSocket对象用来收发数据报，DatagramPacket装报+ip和端口，收发报
 * 主要类 DatagramSocket  DatagramPacket
 */
class UDP{
    public void UDP(){
    }
    public void send(){
        DatagramSocket ds=null;
        try {
            //创建DatagramSocket对象
            ds=new DatagramSocket();
            //装入数据报
            byte[] b="我是要发的数据报".getBytes();
            DatagramPacket pack=new DatagramPacket(b,0,b.length,InetAddress.getByName("127.0.0.1"),9090);
            //DatagramSocket对象发送报
            ds.send(pack);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(ds!=null)
                ds.close();
        }
    }
    public void receive(){
        DatagramSocket ds= null;
        try {
            //链接端口
            ds = new DatagramSocket(9090);
            byte[] b=new byte[1024];
            DatagramPacket pack=new DatagramPacket(b,0,b.length);
            ds.receive(pack);
            String str=new String (pack.getData(),0,pack.getLength());
            System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(ds!=null)
                ds.close();
        }

    }
}

/**
 * URL
 * 主要思想：创建URL类，获取连接的数据流
 * 主要类：URL  URLConnection
 */
class URLTest{
    public static void main(String[] args){
        // 创建URL
        try {
            URL url=new URL("http://192.168.1.100:8080/a.jsp");
            //获取数据流
//            InputStream is=url.openStream();//方式一：无法获取OutputStream
            URLConnection urlConnection=url.openConnection(); //方法二：可以getInputStream和getOutputStream
            InputStream is=urlConnection.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
