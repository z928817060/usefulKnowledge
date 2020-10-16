package com.study.usefulknowledge.配置文件和枚举类;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

/**
 * Created by Administrator on 2017/12/8.
 */
public class spring_read {
    static Car car=null;
    spring_read(){

    }

    public static void main(String[] args) throws Exception {
        //通过类加载器加载配置文件
        Properties properties=new Properties();
        System.out.println(System.getProperty("user.dir")+"\\src\\com\\usefulknowledge\\配置文件和枚举类\\test.properties");
//        InputStream in = spring_read.class.getClassLoader().getSystemResourceAsStream("test.properties");
        InputStream in = new BufferedInputStream(new FileInputStream(System.getProperty("user.dir")+"\\src\\com\\usefulknowledge\\配置文件和枚举类\\test.properties"));
        properties.load(in);
        //遍历配置文件
        Iterator<String> it=properties.stringPropertyNames().iterator();
                     while(it.hasNext()){
                           String key=it.next();
                            System.out.println(key+":"+properties.getProperty(key));
                         }
        in.close();

        /**
         * 读取类名，转化成类
         */
        car=new Car();
        String className=properties.getProperty("VehicleType");
        System.out.println(Class.forName(className).toString());

        Object o=Class.forName(className).newInstance();  //获取名字是className对象的类对象
        car=(Car)o;
    }
}
