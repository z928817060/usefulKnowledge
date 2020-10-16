package com.study.usefulknowledge.java执行linux命令;


import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 功能：本地执行命令行
 * 注意: 每次执行都要恢复默认路径path0  (比如:执行了cd path1,但是下次执行的pwd也是原来的路径path0)
 * 可以执行绝对路径   (比如:sh /home/app/gfs/gfssingle.sh)
 * 执行shell里的路径,最好写成绝对路径,不然会使用path0
 * <p>
 * 执行的命令，可能不能识别，需要写成脚本，然后执行（例如：ps aux|head -1 无法执行识别）
 * Created by wingsby on 2018/3/20.
 */
public class LinuxCommander {
//    static final Logger logger=Logger.getLogger(Logger.class);

    public LinuxCommander() {
    }

    public static void main(String[] args) {
        //sh执行相对绝对路径,./执行相对路径
        /**
         * shell文件bash语法,可以直接执行绝对路径
         */
        String cmd = "sh " + "/home/app/gfs/gfssingle.sh" + " ";
//        String cmd = "wget http://ftp.ncep.noaa.gov/data/nccf/com/gfs/prod/gfs.2018050300/gfs.t00z.pgrb2.0p25.f000";
        System.out.println(cmd);
        //下载数据
        boolean flag = LinuxCommander.exeErrorCmd(cmd, null);
        /**
         * shell文件非bash语法,执行相对的路径+绝对路径的形式
         */
        List<String> pwd = LinuxCommander.getCMDMessage("pwd");       //执行bash,shell语法不兼容
        String[] path = pwd.get(0).split("/");
        String pastPath = "";
        for (int i = 0; i < path.length; i++) {
            pastPath = pastPath + "../";
        }
        cmd = "./" + pastPath + "home/app/xhms/killprocess.sh " + "nmc";
        System.out.println(cmd);
    }

    //这个写法处理了含有通配符的cmd！！！
    public static List<String> getCMDMessage(String cmd) {
        StringBuilder sb = new StringBuilder();
        List<String> list = new ArrayList<String>();
        String[] cmds = {"sh","-c", cmd};
        try {
            Process process = Runtime.getRuntime().exec(cmds);
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            BufferedReader input = new BufferedReader(ir);
            String line = null;
//            logger.info(cmd);
            while ((line = input.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
                list.add(line);
//                logger.error(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    //执行绝对路径,input里面包含有消息的内容
    public static boolean exeCMD(String cmd, List<String> resStr) {
        StringBuilder sb = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec("cd /home/app/xhms");
            process = Runtime.getRuntime().exec(cmd);
            InputStreamReader ir = new InputStreamReader(process.getInputStream());

            BufferedReader input = new BufferedReader(ir);
            String line = null;
//            logger.error(input);
            while ((line = input.readLine()) != null) {
//                logger.error(line);
//                System.out.println(line);
                sb.append(line);
                sb.append('\n');
                if (resStr != null) {
                    resStr.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //执行绝对路径,input里面包含有消息的内容
// cmd:需要执行的命令 resStr给出的反馈信息
    public static boolean exeErrorCmd(String cmd, List<String> resStr) {
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            InputStreamReader ir = new InputStreamReader(process.getErrorStream());
            BufferedReader input = new BufferedReader(ir);
            String line = null;
//            logger.error(input);
            while ((line = input.readLine()) != null) {
//                logger.error(line);
                System.out.println(line);
                if (line.contains("已杀死")) {
                    flag = true;
                }
                sb.append(line);
                sb.append('\n');
                if (resStr != null) {
                    resStr.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return flag;
    }

    public static boolean executeNewFlow(String cmd, List<String> resStr) {
        Runtime run = Runtime.getRuntime();
        Process proc = null;
        try {
            proc = run.exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if (proc != null) {
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())), true);

            //执行的linux命令，（一般复杂流程可以1、shell脚本
            // 2、可以启动一个shell长连接，保持连接，发送多条命令，最后释放连接）
            out.println("cd /home/app/gfs");
//             out.println("rm -fr /home/proxy.log");
            out.println("exit");//这个命令必须执行，否则in流不结束。
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.println(line);
                }
                proc.waitFor();
                in.close();
                out.close();
                proc.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }


}
