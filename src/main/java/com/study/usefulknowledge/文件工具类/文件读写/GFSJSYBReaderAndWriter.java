package com.study.usefulknowledge.文件工具类.文件读写;

import org.apache.commons.logging.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;

public class GFSJSYBReaderAndWriter {
    private static final Log logger = org.apache.commons.logging.LogFactory.getLog(GFSJSYBReaderAndWriter.class);
    public static final int latD = 721;
    public static int lonD = 1440;
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");  //用于格式化数据的类
    static final String[] leveles = new String[]{"0010", "0020", "0030", "0050", "0070", "0100", "0150", "0200", "0250", "0300", "0350", "0400", "0450", "0500", "0550", "0600", "0650", "0700", "0750", "0800", "0850", "0900", "0925", "0950", "0975", "1000"};//26个
    public static final String needLevel = "0100,0200,0300,0400,0500,0600,0650,0700,0750,0800,0850,0900,0925,0950,0975";
    public static final String JSYB_FILENAME_PREFIX = "XHGFS_G_";
    public static final String JSYB_EXPANDED_NAME = ".dat";
    static String basePath = "F:\\1zhuhao\\data\\";
    public static String date="2017072400000";

    public static void main(String[] args){
        long start=System.currentTimeMillis();
        File roadJB= new File("F:\\1zhuhao\\data\\JB\\XHGFS_G_JB_2017072400000.dat");
        File roadDB= new File("F:\\1zhuhao\\data\\DB\\XHGFS_G_DB_2017072400000.dat");
        File road= new File("F:\\1zhuhao\\data\\XHGFS_G_DB_max__2017072400000.dat");
        readAndWriteJBDB(roadJB,roadDB);
        readJBDB_max(road,-90,-180,90,180);
        readJBDB_statistic(road,-90,-180,90,180);
        long end =System.currentTimeMillis();
        System.out.println("time"+(end-start));
    }
    /**
    *读取大文件（105447k的dat），都整合到一起，输出积冰jb、颠簸db
     */
    public static void readAndWriteJBDB(File jbFile, File dbFile) {                           //按照给定气压读取数据
        RandomAccessFile jbrf = null;
        RandomAccessFile dbrf = null;
        try {
            jbrf = new RandomAccessFile(jbFile, "r");    //RandomAccessFile
            dbrf = new RandomAccessFile(dbFile, "r");
            float[][] jbcompare=new float[8][1038240];  //先初始化建立两个“0”比较器
            float[][] dbcompare=new float[8][1038240];
            for (int k = leveles.length - 1; k >= 0; --k) {
                if ("0700,0750,0800,0850,0900,0925,0950,0975".indexOf(leveles[k]) < 0) {
                    dbrf.skipBytes(4152960);    //RandomAccessFile的跳过byte数
                    jbrf.skipBytes(4152960);
                } else {
                    ByteBuffer jbByteBuffer = ByteBuffer.allocate(721 * lonD * 4);
                    ByteBuffer dbByteBuffer = ByteBuffer.allocate(721 * lonD * 4);
                    jbByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                    dbByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                    jbrf.read(jbByteBuffer.array());
                    dbrf.read(dbByteBuffer.array());
                    for (int i = 0; i < 721; ++i) {
                        for (int j = 0; j < 1440; ++j) {
                            float jbValue = jbByteBuffer.getFloat();
                            float dbValue = dbByteBuffer.getFloat();
                            jbcompare[24-k][i*1440+j]=jbValue;  //1038240  对同一地区点值大于0做筛选
                            dbcompare[24-k][i*1440+j]=dbValue;
                        }
                    }
                }
            }
            jbrf.close();
            dbrf.close();
            //对整层数据比较处理
            float[] jbcompare_max=new float[1038240];//最大值
            float[] dbcompare_max=new float[1038240];
            byte[] jbcompareCompare=new byte[1038240];  //先初始化建立两个“0”比较值
            byte[] dbcompareCompare=new byte[721*1440];
            byte[][] jbcompareCompareS=new byte[8][1038240];  //各层比较结果2-8
            byte[][] dbcompareCompareS=new byte[8][721*1440];
            for (int i=0;i<1038240;i++){      //筛选出最大值到jbcompare[7][]
                for (int j=0;j<8;j++){
                   if(j<7) {jbcompare_max[i]=Math.max(jbcompare[j][i],jbcompare[j+1][i]);
                    dbcompare_max[i]=Math.max(jbcompare[j][i],jbcompare[j+1][i]);}
                    if(jbcompare[j][i]>0){jbcompareCompare[i]=(byte)8;jbcompareCompareS[j][i]=(byte)8; }
                    else {jbcompareCompare[i]=(byte)2;jbcompareCompareS[j][i]=(byte)2;}
                    if(dbcompare[j][i]>0){dbcompareCompare[i]=(byte)8;dbcompareCompareS[j][i]=(byte)8; }
                    else {dbcompareCompare[i]=(byte)2;dbcompareCompareS[j][i]=(byte)2;}
                }
            }
            //写入各层press最大值，8层
            for (int k=0;k<8;k++){
                File fileJB_max = new File(basePath+JSYB_FILENAME_PREFIX+"JB_max_"+leveles[24-k]+"_"+date+".dat");
                RandomAccessFile fosJB_max=new RandomAccessFile(fileJB_max,"rw");
                File fileDB_max = new File(basePath+JSYB_FILENAME_PREFIX+"DB_max_"+leveles[24-k]+"_"+date+".dat");
                RandomAccessFile fosDB_max=new RandomAccessFile(fileDB_max,"rw");
                File fileJB= new File(basePath+JSYB_FILENAME_PREFIX+"JB_"+leveles[24-k]+"_"+date+".dat");
                RandomAccessFile fosJB=new RandomAccessFile(fileJB,"rw");
                File fileDB = new File(basePath+JSYB_FILENAME_PREFIX+"DB_"+leveles[24-k]+"_"+date+".dat");
                RandomAccessFile fosDB=new RandomAccessFile(fileDB,"rw");
                for (int j=0;j<721;j++) {  //721
                    byte[] jbBuffer_max=new byte[1440*4];  //1440
                    byte[] dbBuffer_max=new byte[1440*4];
                    byte[] jbBuffer=new byte[1440];  //1440
                    byte[] dbBuffer=new byte[1440];
                    for (int i = 0; i < 1440; i++) {
                        byte[] jbjb_max = float2byte(jbcompare[k][1440*j+i]);
                        byte[] dbdb_max = float2byte(dbcompare[k][1440*j+i]);
                        System.arraycopy(jbjb_max,0,jbBuffer_max,i*4,jbjb_max.length);
                        System.arraycopy(dbdb_max,0,dbBuffer_max,i*4,dbdb_max.length);
                        System.arraycopy(jbcompareCompareS[k],1440*j+i,jbBuffer,i,1);
                        System.arraycopy(dbcompareCompareS[k],1440*j+i,dbBuffer,i,1);
                    }
                    fosJB_max.write(jbBuffer_max);
                    fosDB_max.write(dbBuffer_max);
                    fosJB.write(jbBuffer);
                    fosDB.write(dbBuffer);
                }
                fosJB_max.close();
                fosDB_max.close();
                fosJB.close();
                fosDB.close();
            }
            //写入整理后最大值，一层
            File fileJB_max = new File(basePath+JSYB_FILENAME_PREFIX+"JB_max_"+"_"+date+".dat");
            RandomAccessFile fosJB_max=new RandomAccessFile(fileJB_max,"rw");
            File fileDB_max = new File(basePath+JSYB_FILENAME_PREFIX+"DB_max_"+"_"+date+".dat");
            RandomAccessFile fosDB_max=new RandomAccessFile(fileDB_max,"rw");
            File fileJB = new File(basePath+JSYB_FILENAME_PREFIX+"JB_"+"_"+date+".dat");
            RandomAccessFile fosJB=new RandomAccessFile(fileJB,"rw");
            File fileDB = new File(basePath+JSYB_FILENAME_PREFIX+"DB_"+"_"+date+".dat");
            RandomAccessFile fosDB=new RandomAccessFile(fileDB,"rw");
            for(int j=0;j<721;j++) {       //每次1440*4byte
                byte[] jbBuffer_max=new byte[1440*4];
                byte[] dbBuffer_max=new byte[1440*4];
                byte[] jbBuffer=new byte[1440];
                byte[] dbBuffer=new byte[1440];
                for (int i = 0; i < 1440; i++) {
                    byte[] jbjb_max = float2byte(jbcompare_max[j*1440+i]);
                    byte[] dbdb_max = float2byte(dbcompare_max[j*1440+i]);
                    System.arraycopy(jbjb_max,0,jbBuffer_max,(i)*4,4);
                    System.arraycopy(dbdb_max,0,dbBuffer_max,(i)*4,4);
                    System.arraycopy(jbcompareCompare,1440*j+i,jbBuffer,i,1);
                    System.arraycopy(dbcompareCompare,1440*j+i,dbBuffer,i,1);
                }
                fosJB_max.write(jbBuffer_max);
                fosDB_max.write(dbBuffer_max);
                fosJB.write(jbBuffer);
                fosDB.write(dbBuffer);
            }
            fosJB_max.close();
            fosDB_max.close();
            fosJB.close();
            fosDB.close();
        } catch (FileNotFoundException var27) {
            logger.error(var27);
        } catch (IOException var28) {
            logger.error(var28);
        } finally {
            try {
                if (jbrf != null) {
                    jbrf.close();
                }
                if (dbrf != null) {
                    dbrf.close();
                }
            } catch (IOException var26) {
                logger.error(var26);
            }
        }
    }
    /**
    单层统计值读取（2-8）
     输入：readJBDB_statistic(路径，纬度1、经度1、纬度2、经度2)（经纬度范围[-90 90]，[-180 180]）
     输出：byte[纬度][经度](数据都是按照经纬度从小到大顺序)
     */
    public static float[][] readJBDB_statistic(File file,double lat1,double lon1,double lat2,double lon2){
        RandomAccessFile rf = null;
        if(!((lat1<=90&&lat1>=-90) && (lat2<=90&&lat2>=-90) &&(lon1<=180&&lon1>=-180) &&(lon2<=180&&lon2>=-180))) {
            System.out.println ("请输入纬度，经度[-90 90]，[-180 180]");
            System.exit(0);}
        double latMin=Math.min(lat1,lat2);  //最值经纬度
        double latMax=Math.max(lat1,lat2);
        double lonMin=Math.min(lon1,lon2);
        double lonMax=Math.max(lon1,lon2);
        final double delta = 0.25;       //精度步长
        int lat2num1=(int)Math.floor((latMin+90)/delta);//经纬度2步长数
        int lat2num2=(int)Math.ceil((latMax+90)/delta);
        int lon2num1=(int)Math.floor((lonMin+180)/delta);
        int lon2num2=(int)Math.ceil((lonMax+180)/delta);
        if(lon2num2-lon2num1>=1440)lon2num2--;
        float [][] byteData=new float[lat2num2-lat2num1+1][lon2num2-lon2num1+1];   //步长数
        try {
            if(!file.exists()){
                System.out.println("no file");
                return null;}
            else{
                rf=new RandomAccessFile(file,"r");
                ByteBuffer byteBuffer = ByteBuffer.allocate(latD*lonD);
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                rf.read(byteBuffer.array());
                for(int i=0;i<(lat2num2-lat2num1+1);i++) {
                    for (int j = 0; j < (lon2num2 - lon2num1 + 1);j++){
                        byteData[i][j] = (float)byteBuffer.get((i+lat2num1)*lonD+(j+lon2num1));     //注意;要用get()就好
                    }
                }
            }
        }catch (Exception e){
        }
        return byteData;
    }
    /**
     单层原始数据max读取
     */
    public static float[][] readJBDB_max(File file,double lat1,double lon1,double lat2,double lon2){
        if(!((lat1<=90&&lat1>=-90) && (lat2<=90&&lat2>=-90) &&(lon1<=180&&lon1>=-180) &&(lon2<=180&&lon2>=-180))) {
            System.out.println ("请输入纬度，经度[-90 90]，[-180 180]");
            System.exit(0);}
        RandomAccessFile rf = null;
        double latMin=Math.min(lat1,lat2);  //最值经纬度
        double latMax=Math.max(lat1,lat2);
        double lonMin=Math.min(lon1,lon2);
        double lonMax=Math.max(lon1,lon2);
        final double delta = 0.25;       //精度步长
        int lat2num1=(int)Math.floor((latMin+90)/delta);//经纬度2步长数
        int lat2num2=(int)Math.ceil((latMax+90)/delta);
        int lon2num1=(int)Math.floor((lonMin+180)/delta);
        int lon2num2=(int)Math.ceil((lonMax+180)/delta);
        if(lon2num2-lon2num1>=1440)lon2num2--;
        float [][] byteData=new float[lat2num2-lat2num1+1][lon2num2-lon2num1+1];   //步长数
        try {
            if(!file.exists()){
                System.out.println("no file");
                return null;}
            else{
                rf=new RandomAccessFile(file,"r");
                ByteBuffer byteBuffer = ByteBuffer.allocate(721 * lonD*4);
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                rf.read(byteBuffer.array());
                for(int i=0;i<(lat2num2-lat2num1+1);i++) {
                    for (int j = 0; j < (lon2num2 - lon2num1 + 1);j++){
                        byte[] byte2Float=new byte[4];
                        for (int k=0;k<4;k++){
                            byte2Float[k]=byteBuffer.get(((i+lat2num1)*lonD+(j+lon2num1))*4+k);         //注意;要用get()就好
                        }
                        byteData[i][j] = byteToFloat(byte2Float,0);
                    }
                }
            }
        }catch (Exception e){
        }
        return byteData;
    }

    /**
     * float2byte
     */
    public static byte[] float2byte(float f) {
        // 把float转换为byte[]
        int fbit = Float.floatToIntBits(f);
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (fbit >> (24 - i * 8));
        }
        int len = b.length;
        byte[] dest = new byte[len];
        System.arraycopy(b, 0, dest, 0, len);
        byte temp;
        for (int i = 0; i < len / 2; ++i) {
            temp = dest[i];
            dest[i] = dest[len - i - 1];
            dest[len - i - 1] = temp;
        }
        return dest;
    }

    /**
     * byte2float
     */
    public static final float NULLVAL = -99999.9f;// 表示NULL值
    public static float byteToFloat(byte[] b,int index){
        if(index < 0 || (index + 4) > b.length)
            return NULLVAL;
        int l;
        l = b[index + 0];
        l &= 0xff;
        l |= ((long)b[index + 1] << 8);
        l &= 0xffff;
        l |= ((long)b[index + 2] << 16);
        l &= 0xffffff;
        l |= ((long)b[index + 3] << 24);
        return Float.intBitsToFloat(l);
    }
}