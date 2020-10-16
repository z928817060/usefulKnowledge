package com.study.usefulknowledge.文件工具类.文件读写.对象的读写_文件的形式;

import java.io.*;

/**
 * @Autor rongxiaokun
 * @Date 2018/8/27  16:17
 */
public class Person implements Serializable {
    private String name;
    private int age;
    public Person(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }

    private static void read() throws ClassNotFoundException, IOException {
        //创建序列化流对象
        FileInputStream fis = new FileInputStream("Object.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);
        //读取
        Person obj =null;
        while(fis.available()>0) {
            obj=(Person)ois.readObject();
            System.out.println(obj.name);
        }
        ois.close();
    }

    private static void write() throws IOException {
        //创建序列化流对象
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Object.txt"));
        //创建对象
        Person p1 = new Person("Mike", 22);
        Person p2 = new Person("Like", 44);
        //存入
        oos.writeObject(p1);
        oos.writeObject(p2);
        oos.close();
    }

    public static void main(String[] args) {
        Person person = new Person("lala" , 11);
        try {
//            Person.write();
            Person.read();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //////////////////////////////////////  一次性读取完的

    private static void read1() throws ClassNotFoundException, IOException {
        //创建序列化流对象
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Object.txt"));
        //读出
        Object[] objArr =(Object[])ois.readObject();
        for(Object obj : objArr) {
            System.out.println(obj);
        }
        ois.close();
    }

    private static void write1() throws IOException {
        //创建序列化流对象
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Object.txt"));
        //创建对象
        Person[] p = {new Person("Mike", 22), new Person("Like", 44)};
        //存入
        oos.writeObject(p);
        oos.close();
    }
    }
