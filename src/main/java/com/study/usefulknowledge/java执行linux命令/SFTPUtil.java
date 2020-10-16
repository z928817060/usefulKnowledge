package com.study.usefulknowledge.java执行linux命令;

import com.alibaba.fastjson.JSONObject;
import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


/**
 * Description: 操作sftp主要是jsch包，连接部分主要是Session；功能部分主要是channel
 *              （ChannelSftp封装好的常用功能，ChannelExec执行命令行）
 *
 * @author 作者 <a href=ds.lht@163.com>stone</a>
 * @version 创建时间：2016/8/9.
 */
public class SFTPUtil {

    private static Logger logger = LoggerFactory.getLogger(SFTPUtil.class);
    private static String host = null;
    private static String user = null;
    private static String password = null;
    private static int port = 0;
    private static int timeOut = 10000;

    private static Session session = null;

    //初始化session
    static {
        try {
            JSch jsch = new JSch();
            session = jsch.getSession("", "", 0);
        } catch (JSchException e) {
            logger.error("获取SFTP连接发生异常！地址：{}", host, e);
        }
    }

    /**
     * @param host
     * @param user
     * @param password
     * @param port
     * @param timeOut   ms
     * @return
     * @throws JSchException
     */
    public static Session getConnection(String host, String user, String password,
                                        int port, int timeOut) throws JSchException {
        /**
         * 策略:与上次ip相同,则不重复连接
         */
        if (host.equals(SFTPUtil.getHost()) && session.isConnected()){return session;}
        closeSession(session);
        SFTPUtil.host = host;
        SFTPUtil.user = user;
        SFTPUtil.password = password;
        SFTPUtil.port = port;
        SFTPUtil.timeOut = timeOut;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);
            session.setPassword(password);
            //设置第一次登陆的时候提示，可选值:(ask | yes | no)
            session.setConfig("StrictHostKeyChecking", "no");
            //30秒连接超时
            session.connect(timeOut);
        } catch (JSchException e) {
            logger.error("获取SFTP连接发生异常！地址：{}", host, e);
//            throw e;
        }
        return session;
    }


    /**
     * sftp上传文件(夹)
     *
     * @param directory
     * @param sftp
     * @param uploadFile
     * @throws Exception
     */
    protected static void upload(String directory, ChannelSftp sftp, String... uploadFile) throws Exception {

        createDir(directory, sftp);
        for (String fileStr : uploadFile) {
            File file = new File(fileStr);
            InputStream ins = null;
            if (file.exists()) {
                //这里有点投机取巧，因为ChannelSftp无法去判读远程linux主机的文件路径,无奈之举
                try {
                    //进入目标路径
                    sftp.cd(directory);

                    if (file.isFile()) {
                        ins = new FileInputStream(file);
                        //中文名称的
                        sftp.put(ins, new String(file.getName().getBytes(), "UTF-8"));
                    } else {
                        logger.warn("{}是文件夹，不支持文件夹上传!", fileStr);
                    }
                } catch (SftpException e) {
                    logger.error("文件上传失败,文件路径：{}", fileStr, e);
                    throw e;
                } finally {
                    if (ins != null) {
                        ins.close();
                    }
                }
            } else {
                throw new FileNotFoundException("上传的文件不存在，" + fileStr);
            }
        }
    }

    private static void createDir(String directory, ChannelSftp sftp) throws Exception {
        try {
            if (directory != null) {
                String[] dirs = directory.split("/");
                StringBuffer dirsb = new StringBuffer("/");
                if (dirs != null && dirs.length > 0) {
                    for (String dir : dirs) {
                        if (dir == null || dir.trim().equals(""))
                            continue;
                        dirsb.append(dir + "/");
                        if (!isDirExist(dirsb.toString(), sftp)) {
                            sftp.mkdir(dirsb.toString());
                        }
                        sftp.cd(dirsb.toString());
                    }
                }
            }
        } catch (SftpException e) {
            logger.error("创建目录异常！", e);
            throw e;
        }
    }

    public static boolean isDirExist(String directory, ChannelSftp sftp) {
        boolean isDirExistFlag = false;
        try {
            SftpATTRS sftpATTRS = sftp.lstat(directory);
            sftpATTRS = sftp.stat(directory);
            return sftpATTRS.isDir();
        } catch (Exception e) {
            //目录不存在
            isDirExistFlag = false;
        }
        return isDirExistFlag;
    }

    /**
     * 功能：获取ssh/sftp的路径下的文件名
     *
     * @param session
     * @param path
     * @return
     */
    public static List<String> getFileNames(Session session, String path) {
        Channel channel = null;
        ChannelSftp sftp = null;
        List<String> list = new ArrayList<>();
        try {
            channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            Vector<ChannelSftp.LsEntry> v = sftp.ls(path);
            for (ChannelSftp.LsEntry ch : v) {
                list.add(ch.getFilename());
            }
            channel.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // TODO: 2018/7/3 执行exe的方法工具
    public static List<String> exec(Session session ,String command) {
        // command=args[1];
        Channel channel = null;
        List<String> result = new ArrayList<>();
        try {
            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            //channel.setInputStream(System.in);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            InputStream in = channel.getInputStream();
            channel.connect();
            byte[] tmp = new byte[1024];
            //写成String
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));
            String buf = null;

            while ((buf = reader.readLine()) != null) {
//                result += new String(buf.getBytes("gbk"), "UTF-8")
//                result += new String(buf.getBytes("UTF-8"), "UTF-8") + " <br>\r\n";
                result.add( new String(buf.getBytes("UTF-8"), "UTF-8") );
            }
//            while (true) {
//                while (in.available() > 0) {
//                    int i = in.read(tmp, 0, 1024);
//                    if (i < 0) {
//                        break;
//                    }
//                    System.out.print(new String(tmp, 0, i));
//                }
//                if (channel.isClosed()) {
//                    System.out.println("exit-status: " + channel.getExitStatus());
//                    break;
//                }
//                try {
//                    Thread.sleep(1000);
//                } catch (Exception ee) {
//                    ee.printStackTrace();
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (channel!=null){
                channel.disconnect();
            }
        }
        return result;
    }

    public static void closeSession(Session session){
        if (session != null && session.isConnected()){
            session.disconnect();
        }
    }

    //通过ip获得连接服务器的参数
    public static JSONObject getConnetPara(String ip){
        JSONObject json_para = new JSONObject();
        for (int i = 1; i < 100; i++) {
            if (PropertyUtil.getProperty("server_host_" + i) == null ){break;}
            if (PropertyUtil.getProperty("server_host_" + i).equals(ip)) {
                json_para.put("host", PropertyUtil.getProperty("server_host_" + i));
                json_para.put("user", PropertyUtil.getProperty("server_user_" + i));
                json_para.put("password", PropertyUtil.getProperty("server_password_" + i));
                json_para.put("port", PropertyUtil.getProperty("server_port_" + i));
                json_para.put("timeOut", PropertyUtil.getProperty("server_timeOut_" + i));
            }
        }
        return json_para;
    }

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        SFTPUtil.host = host;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        SFTPUtil.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        SFTPUtil.password = password;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        SFTPUtil.port = port;
    }

    public static int getTimeOut() {
        return timeOut;
    }

    public static void setTimeOut(int timeOut) {
        SFTPUtil.timeOut = timeOut;
    }

    public static Session getSession() {
        return session;
    }

    public static void setSession(Session session) {
        SFTPUtil.session = session;
    }

    public static void main(String[] args) {
        try {
            Session session = getConnection("192.6.1.20","app","app82193302",22,10000);
            List<String> s = exec(session,"");
            if (!(s == null || s.isEmpty() )) {
                for (int i = 0; i <s.size(); i++) {
                    System.out.println(s.get(i));
                }
            }
            closeSession(session);
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
}
