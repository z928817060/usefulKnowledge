package com.study.usefulknowledge.文件工具类.文件读写;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

/**
 * 文件读写、copy，文本txt一般用字符流（但是要求不同），其他一般用字节流
 */
public class FileReadWrite读写文件 {


    public static void readData(File jbFile) throws IOException {

/**读取文件 */
        try {
            RandomAccessFile jbrf = new RandomAccessFile(jbFile, "r");    //RandomAccessFile读写文件
            jbrf.skipBytes(4152960);    //RandomAccessFile的跳过 byte数
            FileInputStream fileInputStream = new FileInputStream(jbFile);
            ByteBuffer jbByteBuffer = ByteBuffer.allocate(721 * 1440 * 4);    //构建缓冲
            jbByteBuffer.order(ByteOrder.LITTLE_ENDIAN);     //小头存储
            jbrf.read(jbByteBuffer.array());
            ArrayList<Float> jbcompare = new ArrayList<Float>();
            for (int i = 0; i <= 100; i++) {
                jbcompare.add(jbByteBuffer.getFloat());      //每次读取一个值去处理
            }
            //注意：读取文件的readline()时，需要中文转码：
            //*******************************************
            String res = "";
            while (true) {
                String lineString = jbrf.readLine();
                if (lineString == null) break;
                res += new String(lineString.getBytes("ISO-8859-1"), "utf-8") + "\n";
            }
            //*******************************************
/**写入文件*/
            //写入文本，字符流
            BufferedWriter jbbuf = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("F:\\1zhuhao\\data\\JB.txt")));
            for (int ii = 0; ii <= 0; ii++) {
                String s = new String();
                s = String.valueOf(jbcompare.subList(1440 * ii, (1440 * ii + 1440)));
                s = s.substring(1, s.length() - 1);
                jbbuf.write(s + "," + "\r\n");
                jbbuf.write(jbcompare + "," + "\r\n");  //换行
            }
            jbbuf.close();//关闭流

            //写入文本，二进制字节    都是先写成二进制数组，再直接输出
            byte[] jbbyte = new byte[1038240];  //先初始化建立两个“0”比较器
            File fileJB = new File("F:\\1zhuhao\\data\\JB.dat");
            FileOutputStream fosJB = new FileOutputStream(fileJB);
            fosJB.write(jbbyte);
            fosJB.close();

        } catch (Exception e) {
            //异常处理
        }

    }

    /**
     * 文件的快速复制，也可以自己用BufferedInputStream读取字节流，BufferedReader读取字符流，BufferedOutputStream写入字节流，BufferedWriter写入字符流.
     *
     * @param source
     * @param dest
     */
    private static void copyFileUsingFileChannels(File source, File dest) {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } catch (Exception e) {

        } finally {
            try {
                inputChannel.close();
                outputChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 合并文件：追加
     * @param path  需要合并的文件夹路径
     * @param combfile  目标文件
     */
    public static void addFile(String path,String combfile) {
        FileChannel mFileChannel = null;
        try {
            mFileChannel = new FileOutputStream(combfile).getChannel();
            FileChannel inFileChannel;
            File[] fins = new File(path).listFiles();
            for (File fin : fins) {
                inFileChannel = new FileInputStream(fin).getChannel();
                inFileChannel.transferTo(0, inFileChannel.size(),
                        mFileChannel);
                inFileChannel.close();
            }
            mFileChannel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
