package com.study.usefulknowledge.java执行linux命令;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Created by shijunna on 2016/8/10.
 */
public class DownloadUtil_获取本机ip {

    /**
     * 数据下载开始之前的预处理
     * 将处在downloading状态的数据时次删除，使程序重新启动时，该时次能继续下载
     * @param path
     */
//    public static void preDownload(String path) {
//        Map<String, String> filenameMap = FileHandler.getFilenameMap(path);
//        Map<String, String> map = new HashMap<>();
//        if(filenameMap != null && filenameMap.size() > 0){
//            Iterator<String> iterator = filenameMap.keySet().iterator();
//            while(iterator.hasNext()){
//                String next = iterator.next();
//                if(filenameMap.get(next).equals(FileHandler.downloading)){
////                    filenameMap.remove(next);
//                }else{
//                    map.put(next,filenameMap.get(next));
//                }
//            }
//        }
//        FileHandler.map2File(map,path);
//    }

    public static String localIP = getLocalIP();

    private static String getLocalIP()  {
//        return "192.168.1.114";
        String sIP = null;
        InetAddress ip = null;
        try {
            //如果是Windows操作系统
            if(isWindowsOS()){
                ip = InetAddress.getLocalHost();
                sIP = ip.getHostAddress();
            }else {//如果是Linux操作系统
//                sIP = "192.168.1.114";
                Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
                while (allNetInterfaces.hasMoreElements())
                {
                    NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                    System.out.println(netInterface.getName());
                    Enumeration addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements())
                    {
                        ip = (InetAddress) addresses.nextElement();
                        if (ip != null && ip instanceof Inet4Address)
                        {
                            System.out.println("本机的IP = " + ip.getHostAddress());
                            sIP=ip.getHostAddress();
                        }
                    }
                }
            }
            return sIP;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "192.168.1.114";
        }
    }


    public static boolean isWindowsOS(){
        boolean isWindowsOS = false;
        String osName = System.getProperty(".name");
        if(osName.toLowerCase().indexOf("windoosws")>-1){
            isWindowsOS = true;
        }
        return isWindowsOS;
    }
//    public String getMacAddr() {
//        String MacAddr = "";
//        String str = "";
//        try {
//            NetworkInterface NIC = NetworkInterface.getByName("eth0");
//            byte[] buf = NIC.getHardwareAddress();
//            for (int i = 0; i < buf.length; i++) {
//                str = str + byteHEX(buf[i]);
//            }
//            MacAddr = str.toUpperCase();
//        } catch (SocketException e) {
//            e.printStackTrace();
//            System.exit(-1);
//        }
//        return MacAddr;
//    }
    public static String byteHEX(byte ib) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a','b', 'c', 'd', 'e', 'f' };
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }


}
