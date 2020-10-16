package com.study.usefulknowledge.路径的设置;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class 获取项目路径 {
    获取项目路径() {

    }

    public void main(String[] args) {
        //系统路径，但是多个modle时，可能是启动路径（不建议）
        String path = System.getProperty("user.dir") + "/classes/";//获取项目路径
        this.getClass();  //获取本类的class路径

        /**
         * 注意：静态中，可能会有加载顺序的问题，可以使用class.getProtectionDomain().getCodeSource().getLocation().getPath()  代替class.getClassLoader().getResource("").getPath()
         *
         * war 一般使用“类加载器”的的路径               （可以直接File操作）
         * 1)文件操作（File）:  获取项目路径.class.getClassLoader().getResource("").getPath()+"config/readGrib.properties";
         * 2)直接读取文件流：InputStream io=PrdJSONUtils.class.getClassLoader().getResourceAsStream(path);
         *
         * jar 一般使用“类”的相对路径和项目下的绝对路径   （无法直接File操作）
         * 1)文件操作（File）:  文件外置，外部路径：String path = new File(DateConUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile().getPath()+"/cond/Pest/";
         * 2)直接读取文件流：InputStream io=PrdJSONUtils.class.getClassLoader().getResourceAsStream(path);
         */
        //war包
        //表示项目路径下的target路径   ，也是编译后的classes路径
        //由于可以自动生成文件夹的项目，所以支持文件的操作
        String springPath = 获取项目路径.class.getClassLoader().getResource("").getPath() + "config/readGrib.properties";
        //StationUtil.class.getClassLoader().getResource("").getPath() + "station_china.json";
        //StationUtil.class.getClassLoader().getResource("/").getPath() + "station_china.json";项目名的路径，类在项目中的绝对根路径
        //ClassLoader.getSystemResourceAsStream(filePath);也可以使用

        //jar包
        //由于构造器位置不同,导致和war包的打包路径不同
        //之所以不能读取Jar包中的文件，这主要是因为jar包是一个单独的文件而非文件夹，绝对不可能通过file:/e:/.../ResourceJar.jar/resource /res.txt这种形式的文件URL来定位res.txt。
        path = 获取项目路径.class.getClass().getResource("").getPath() + "/config/readGrib.properties";
        //StationUtil.class.getResource("").getPath() + "station_china.json";和StationUtil同路径，类相对路径
        //StationUtil.class.getResource("/").getPath() + "station_china.json";项目名的路径，类在项目中的绝对根路径


        /**
         * 一般情况用构造器的路径,但是静态方法中,构造器路径不好使用
         */

        //读取文件的io操作
        public static JSONObject loadTobaccoMeteoCondtionJSON (String path) throws IOException {
            JSONObject res = null;
            InputStream io = PrdJSONUtils.class.getClassLoader().getResourceAsStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(io));
            String str = null;
            StringBuilder stringBuilder = new StringBuilder();
            while ((str = reader.readLine()) != null) {
                stringBuilder.append(str);
            }
            res = JSON.parseObject(stringBuilder.toString());
            io.close();
            return res;
        }
    }
}
