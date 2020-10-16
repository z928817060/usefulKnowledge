package com.study.usefulknowledge.�ļ�������.�ļ���д;


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
     * ʵ����ָ��λ��
     * ��������
     * @param fileName �ļ���
     * @param points ָ��λ��
     * @param insertContent ��������
     * **/
    public static void insert(String fileName,long points,String insertContent){
        try{
            File tmp= File.createTempFile("tmp", null);
            tmp.deleteOnExit();//��JVM�˳�ʱɾ��

            RandomAccessFile raf=new RandomAccessFile(fileName, "rw");
            //����һ����ʱ�ļ�������������������
            FileOutputStream tmpOut=new FileOutputStream(tmp);
            FileInputStream tmpIn=new FileInputStream(tmp);
            raf.seek(points);
            /**������������ݶ�����ʱ�ļ���**/

            byte [] buff=new byte[1024];
            //���ڱ�����ʱ��ȡ���ֽ���
            int hasRead=0;
            //ѭ����ȡ�����������
            while((hasRead=raf.read(buff))>0){
                // ����ȡ������д����ʱ�ļ���
                tmpOut.write(buff, 0, hasRead);
            }

            //������Ҫָ����ӵ�����
            raf.seek(points);//����ԭ���Ĳ��봦
            //׷����Ҫ׷�ӵ�����
            raf.write(insertContent.getBytes("UTF-8"));
            //���׷����ʱ�ļ��е�����
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
        // randomRed(path,seekPointer);//��ȡ�ķ���
        // randomWrite(path);//׷��д�ķ���
        insert(path, 33, "\nlucene��һ�������ȫ�ļ�����");
    }
}
