package com.study.usefulknowledge.文件工具类.文件读写;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

/**
 * @Autor rongxiaokun
 * @Date 2018/12/25  13:43
 */
public class RandomAccessFileWrite {
//    private static final Logger LOGGER = LoggerFactory.getLogger(RandomAccessFileWrite.class);


    /**
     * 实现向指定位置
     * 插入数据
     * @param fileName 文件名
     * @param points 指针位置
     * @param insertContent 插入内容
     * **/
    public static void insert(String fileName,long points,String insertContent){
        try{
            File tmp= File.createTempFile("tmp", null);
            tmp.deleteOnExit();//在JVM退出时删除

            RandomAccessFile raf=new RandomAccessFile(fileName, "rw");
            //创建一个临时文件夹来保存插入点后的数据
            FileOutputStream tmpOut=new FileOutputStream(tmp);
            FileInputStream tmpIn=new FileInputStream(tmp);
            raf.seek(points);
            /**将插入点后的内容读入临时文件夹**/

            byte [] buff=new byte[1024];
            //用于保存临时读取的字节数
            int hasRead=0;
            //循环读取插入点后的内容
            while((hasRead=raf.read(buff))>0){
                // 将读取的数据写入临时文件中
                tmpOut.write(buff, 0, hasRead);
            }

            //插入需要指定添加的数据
            raf.seek(points);//返回原来的插入处
            //追加需要追加的内容
            raf.write(insertContent.getBytes("UTF-8"));
            //最后追加临时文件中的内容
            while((hasRead=tmpIn.read(buff))>0){
                raf.write(buff,0,hasRead);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        String path="D:\\3\\test.txt";
        //int seekPointer=20;
        // randomRed(path,seekPointer);//读取的方法
        // randomWrite(path);//追加写的方法
        insert(path, 33, "\nlucene是一个优秀的全文检索库");
    }
}
