package com.study.usefulknowledge.文件工具类.读取excel;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

//碰到的问题：xls里数据会有数字加字符的情况，需要手动将字符修改
public class GetExcelInfo {
    private String[] westToEast_m= new String[]{"860","862","864","865","866","868","870","872","874","875","876","877","878","880","882","883","884",
            "885","886","887","888","889","890","891","892","893","894"};
    private static final DecimalFormat decimalFormat = new DecimalFormat("000");  //用于格式化数据的类
    int lat_start=18; //lat范围
    int lat_end=90;//对应北半球（18，162）
    int lat_d=24;
    int lon_start=0; //lon范围
    int lon_end=360;
    int lon_d=30;
    HashSet<String> westToEast=new HashSet<String>();
    HashSet<String> eastToWest=new HashSet<String>();
    GetExcelInfo(){
        for(String s:westToEast_m){
            westToEast.add(s);
        }
        for(int i=1093;i<=1110;i++){
            eastToWest.add(Integer.toString(i));
        }
    }
    public static void main(String[] args) {
        GetExcelInfo obj = new GetExcelInfo();
        // 此处为我创建Excel路径：E:/zhanhj/studysrc/jxl下  
        File file = new File("C:\\Users\\Administrator\\Desktop\\海洋气候统计网格点划分_test.xls");
//        List[] lists=obj.getSouthGrids(file);
//        HashMap<String ,ArrayList<Integer>> hashMap=obj.insertAreaDegree(file);
        HashMap<String ,ArrayList<ArrayList<Integer>>> hashMap=obj.getSouthGrids(file);
    }


    /**
     * 南半球数据转化格点：经纬度（0：180，0：360）---   floor(lon/0.5)*1000+floor(lat+90)/0.5  ---格点grids
     * @param file
     * @return  lists:维数：（表格sheet数，表格里的行数，列数）
     */
//    public List[] getSouthGrids(File file) {
//        List[] lists=new ArrayList[2];
//        ArrayList<ArrayList<Integer>> list_mm = null;
//        try {
//            // 创建输入流，读取Excel
//            InputStream is = new FileInputStream(file.getAbsolutePath());
//            // jxl提供的Workbook类
//            Workbook wb = Workbook.getWorkbook(is);
//            // Excel的页签数量
////            int sheet_size = wb.getNumberOfSheets();
//            int sheet_size=2;
//            for (int index = 0; index < sheet_size; index++) {
//                list_mm=new ArrayList<ArrayList<Integer>>();
//                // 每个页签创建一个Sheet对象
//                Sheet sheet = wb.getSheet(index);
//                // sheet.getRows()返回该页的总行数
//                for (int i = 1; i < sheet.getRows(); i++) {  //第一行为说明，不用
//                    String string_row=sheet.getCell(0, i).getContents();  //返回该页的总行数,但是返回了好多“”空
//                    if(string_row.isEmpty())break;
//                    int col_num=sheet.getColumns();//返回该页的总列数,但是返回了好多“”空
//                    String[] latlon=new String[12];
//                    for (int j = 1; j < col_num; j++) {
//                        String string=sheet.getCell(j, i).getContents();
//                        if (string.isEmpty())
//                            break;
//                        latlon[j-1] = string;
//                    }
//                    List<Float> lat_grid =getlat(latlon[0],latlon[1],1);
//                    List<Float> lon_grid =null;
//                    if(index==0&&westToEast.contains(Integer.toString(i+1))){
//                        lon_grid =getlon(latlon[2],latlon[3],2);
//                    }else if(index==1&&eastToWest.contains(Integer.toString(i+1))){
//                        lon_grid =getlon(latlon[2],latlon[3],3);
//                    }else {
//                        lon_grid =getlon(latlon[2],latlon[3],index);
//                    }
//                    ArrayList<Integer> list_m=new ArrayList<Integer>();
//                    for (Float lat:lat_grid ) {  //存储的每行数据：从小经度开始，经度走完再走下一个纬度
//                        for (Float lon : lon_grid) {
//                            int grid=(int) (Math.floor(lon / 0.5) * 1000 + Math.floor(lat / 0.5));
//                            list_m.add(grid);
//                        }
//                    }
//                    list_mm.add(list_m);
//
//
//                }
//                lists[index]=list_mm;
//            }
//            return lists;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (BiffException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//
//        }
//        return lists;
//    }

    /**
     * 南半球数据转化格点：经纬度（0：180，0：360）---   floor(lon/0.5)*1000+floor(lat+90)/0.5  ---格点grids
     * @param file
     * @return  lists:维数：（表格sheet数，表格里的行数，列数）
     * 更改1：2017.10.23：显示格点的经纬度latlon--〉lonlat，位置在hashmap初始化，以及得到的hashset_string
     */
    public HashMap<String ,ArrayList<ArrayList<Integer>>> getSouthGrids(File file) {
        HashMap<String ,ArrayList<ArrayList<Integer>>> hashMap = new HashMap<String, ArrayList<ArrayList<Integer>>>(); //整个区域
        ArrayList<ArrayList<Integer>> list_mm = null; //每个区域
        ArrayList<Integer> list_m=null; //每行
        for(int i=lat_start; i<lat_end; i=i+lat_d) {  //lat(-72:24:72)
            for (int j = lon_start; j < lon_end; j = j + lon_d) {
                hashMap.put(decimalFormat.format(j)+decimalFormat.format(i),new ArrayList<ArrayList<Integer>>());
            }
        }
        try {
            // 创建输入流，读取Excel
            InputStream is = new FileInputStream(file.getAbsolutePath());
            // jxl提供的Workbook类
            Workbook wb = Workbook.getWorkbook(is);
            // Excel的页签数量
//            int sheet_size = wb.getNumberOfSheets();
            int sheet_size=2;
            for (int index = 0; index < sheet_size; index++) {
                list_mm=new ArrayList<ArrayList<Integer>>();
                // 每个页签创建一个Sheet对象
                Sheet sheet = wb.getSheet(index);
                // sheet.getRows()返回该页的总行数
                for (int i = 1; i < sheet.getRows(); i++) {  //第一行为说明，不用
                    String string_row=sheet.getCell(0, i).getContents();  //返回该页的总行数,但是返回了好多“”空
                    if(string_row.isEmpty())break;
                    int col_num=sheet.getColumns();//返回该页的总列数,但是返回了好多“”空
                    String[] latlon=new String[12];
                    for (int j = 1; j < col_num; j++) {
                        String string=sheet.getCell(j, i).getContents();
                        if (string.isEmpty())
                            break;
                        latlon[j-1] = string;
                    }
                    List<Float> lat_grid =getlat(latlon[0],latlon[1],1);
                    List<Float> lon_grid =null;
                    if(index==0&&westToEast.contains(Integer.toString(i+1))){
                        lon_grid =getlon(latlon[2],latlon[3],2);
                    }else if(index==1&&eastToWest.contains(Integer.toString(i+1))){
                        lon_grid =getlon(latlon[2],latlon[3],3);
                    }else {
                        lon_grid =getlon(latlon[2],latlon[3],index);
                    }
                    list_m=new ArrayList<Integer>();
                    HashSet<String> hashSet_string=new HashSet<String>();
//                    HashMap<String,ArrayList<Integer>> hashMap_m=new HashMap<String,ArrayList<Integer>>();
                    for (Float lat:lat_grid ) {  //存储的每行数据：从小经度开始，经度走完再走下一个纬度
                        for (Float lon : lon_grid) {
                            int grid=(int) (Math.floor(lon / 0.5) * 1000 + Math.floor(lat / 0.5));
                            list_m.add(grid);
                            ////获取对应格点区域
                            ArrayList<Double>[] arrayLists=getArea_latlon(lat,lon);
                            for(Double lat_double:arrayLists[0]){
                                for(Double lon_double:arrayLists[1]){
//                                    if (hashMap_m.get(decimalFormat.format(lat_double)+decimalFormat.format(lon_double))==null) {
//                                        list_m = new ArrayList<Integer>();
//                                        list_m.add(grid);
//                                        hashMap_m.put((decimalFormat.format(lat_double) + decimalFormat.format(lon_double)),list_m);
//                                    }else {
//                                        hashMap_m.get(decimalFormat.format(lat_double) + decimalFormat.format(lon_double)).add(grid);
//                                    }
                                    hashSet_string.add(decimalFormat.format(lon_double)+decimalFormat.format(lat_double));
                                }
                            }
                            //
                        }
                    }
                    for(String string  :  hashSet_string){
                        hashMap.get(string).add(list_m);
                    }
                }
            }
            return hashMap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
        return hashMap;
    }
    /**
     *
     * @param file
     * @return HashMap<String(区域坐标标识：000000：前三位表示起始点lat，后三位表示起始点lon) ,ArrayList<Integer>（根据公式给出格点计算结果）>
     *     hashMap  ，公式：floor(lon/0.5)*1000+lat/0.5（latlon的取值范围[0，180][0，360]）
     */
    public HashMap<String ,ArrayList<Integer>> insertAreaDegree(File file) {
        HashMap<String ,ArrayList<Integer>> hashMap =new HashMap<String, ArrayList<Integer>>();
        for(int i=lat_start; i<lat_end; i=i+lat_d) {  //lat(-72:24:72)
            for (int j = lon_start; j < lon_end; j = j + lon_d) {
                hashMap.put(decimalFormat.format(j)+decimalFormat.format(i),new ArrayList<Integer>());
            }
        }
        try {
            // 创建输入流，读取Excel
            InputStream is = new FileInputStream(file.getAbsolutePath());
            // jxl提供的Workbook类
            Workbook wb = Workbook.getWorkbook(is);
            // Excel的页签数量
//            int sheet_size = wb.getNumberOfSheets();
            int sheet_size=2;
            for (int index = 0; index < sheet_size; index++) {
                // 每个页签创建一个Sheet对象
                Sheet sheet = wb.getSheet(index);
                // sheet.getRows()返回该页的总行数
                for (int i = 0; i < sheet.getRows(); i++) {  //第一行为说明，不用
                    String string_row=sheet.getCell(0, i).getContents();  //返回该页的总行数,但是返回了好多“”空
                    if(string_row.isEmpty())break;
                    int col_num=sheet.getColumns();//返回该页的总列数,但是返回了好多“”空
                    String[] latlon=new String[12];
                    for (int j = 1; j < col_num; j++) {
                        String string=sheet.getCell(j, i).getContents();
                        if (string.isEmpty())
                            break;
                        latlon[j-1] = string;
                    }
                    List<Float> lat_grid =getlat(latlon[0],latlon[1],1);
                    List<Float> lon_grid =null;
                    if(index==0&&westToEast.contains(Integer.toString(i+1))){
                        lon_grid =getlon(latlon[2],latlon[3],2);
                    }else if(index==1&&eastToWest.contains(Integer.toString(i+1))){
                        lon_grid =getlon(latlon[2],latlon[3],3);
                    }else {
                        lon_grid =getlon(latlon[2],latlon[3],index);
                    }
                    ArrayList<Integer> list_m=new ArrayList<Integer>();
                    for (Float lat:lat_grid ) {  //存储的每行数据：从小经度开始，经度走完再走下一个纬度
                        for (Float lon : lon_grid) {
                            int grid=(int) (Math.floor(lon / 0.5) * 1000 + Math.floor(lat / 0.5));
//                            list_m.add(grid);
                            ////获取对应格点区域
                            ArrayList<Double>[] arrayLists=getArea_latlon(lat,lon);
                            for(Double lat_double:arrayLists[0]){
                                for(Double lon_double:arrayLists[1]){
                                    hashMap.get(decimalFormat.format(lon_double)+decimalFormat.format(lat_double)).add(grid);
                                }
                            }
                            //
                        }
                    }
                }
            }
            return hashMap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
        return hashMap;
    }

    /**
     * latlon转成
     * @param lat
     * @param lon
     * @return  格点分类的经纬度 arrayLists[0]：纬度  arrayLists[1]：经度
     */
    public ArrayList<Double>[] getArea_latlon(Float lat, Float lon){
        double lat0= Math.floor((lat-lat_start)/lat_d)*lat_d+lat_start;  //按照向低位取整的办法查看落点
        double lon0= Math.floor(lon/30)*30;
        ArrayList<Double> arrayList_lat=new ArrayList<Double>();
        ArrayList<Double> arrayList_lon=new ArrayList<Double>();
        ArrayList<Double>[] arrayLists=new ArrayList[2];
        if (lat>lat0){
            arrayList_lat.add(lat0);
        }else{
            if(lat0==lat_start){
                arrayList_lat.add(lat0);
            }else if(lat0==lat_end){
                arrayList_lat.add(lat0-lat_d);
            }else {
                arrayList_lat.add(lat0);
                arrayList_lat.add(lat0 - lat_d);
            }
        }
        if (lon>lon0){
            arrayList_lon.add(lon0);
        }else{
            if(lon0==lon_start){
                arrayList_lon.add(lon0);
            }else if(lon0==lon_end){
                arrayList_lon.add(lon0-lon_d);
            }else {
                arrayList_lon.add(lon0);
                arrayList_lon.add(lon0 - lon_d);
            }
        }
        arrayLists[0]=arrayList_lat;
        arrayLists[1]=arrayList_lon;
        return arrayLists;
    }


    //纬度格点：method=0(北纬)  ，method=1(南纬)
    public List<Float> getlat(String lat11, String lat22, int method){
        List<Float> list=new ArrayList<Float>();
        float lat1=0;float lat2=0;
        if(method==1) {  //南
            lat1 = 90 - Float.parseFloat(lat11);
            lat2 = 90 - Float.parseFloat(lat22);
        }
        if(method==0) { //北
            lat1 = 90 + Float.parseFloat(lat11);
            lat2 = 90 + Float.parseFloat(lat22);
        }
        if (lat1>lat2){
            float f=lat1;
            lat1=lat2;
            lat2=f;
        }
        float f=lat1;
        while (f<lat2){
            list.add(f);
            f=(float) (f+0.5);
        }
        return list;
    }
    //经度格点：method=0(西经)  ，method=1(东经)
    public List<Float> getlon(String lon11, String lon22, int method){
        List<Float> list=new ArrayList<Float>();
        float lon1=0;float lon2=0;
        if(method==0) {  //西
            lon1 = 180 - Float.parseFloat(lon11);
            lon2 = 180 - Float.parseFloat(lon22);
        }
        if(method==1){  //东
            lon1 = 180 + Float.parseFloat(lon11);
            lon2 = 180 + Float.parseFloat(lon22);
        }
        if(method==2){  //西-东
            lon1 = 180 - Float.parseFloat(lon11);
            lon2 = 180 + Float.parseFloat(lon22);
        }
        if(method==3){  //东-西
            lon1 = 180 + Float.parseFloat(lon11);
            lon2 = 180 - Float.parseFloat(lon22);
        }
//        if (lon1>lon2){
//            float f=lon1;
//            lon1=lon2;
//            lon2=f;
//        }
        float f=lon1;
        if(lon1-lon2>180){
            float lon3=lon2+360;
            while (f<lon3){
                if(f>=360){
                    list.add(f-360);
                }else{
                list.add(f);
                }
                f=(float) (f+0.5);
            }
        }
        while (f<lon2){
            list.add(f);
            f=(float) (f+0.5);
        }
        return list;
    }
}  