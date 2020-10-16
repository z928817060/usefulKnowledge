package com.study.usefulknowledge.查询和排序;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 功能: list定制排序
 * 方案:  数据类:Person + 排序方式:sortByHeightAndWeight
 * 思路：利用list的sort方法，重写Comparator实现
 * 注意：Comparator的实现   从小到大排序（-1 ，0 ，1）
 *                      if (o1.weight < o2.weight)
                            return -1;
                        if (o1.weight > o2.weight)
                            return 1;
                        return 0;
 */

public class goTest1 {
    public static List<Person> sortBy(List<Person> list,String key){
        if(key == "Height"){
            list.sort(new sortByHeightAndWeight());
        }
//自己写其他的
        return list;
    }
    public static void main(String[] agrs){
        Person A = new Person("Alex","001", 180.2, 160, 6000);
        Person B = new Person("Tom","002", 171.2, 180, 2000);
        Person C = new Person("Jerry","003",171.2, 170, 3000);
        Person D = new Person("Bob","004", 171.2, 175, 6200);
        List<Person> list = new ArrayList<Person>();
        list.add(A);
        list.add(B);
        list.add(C);
        list.add(D);
        for(Person p:list){
            System.out.println(p.name);
        }
        System.out.println();
// list.sort(new sortByHeight());
        goTest1.sortBy(list, "Height");
        for(Person p:list){
            System.out.println(p.name);
        }
    }
}

class sortByHeight implements Comparator<Person>{           //单要素排序规则重写
    @Override
    public int compare(Person arg0, Person arg1) {
        return Double.compare(arg0.height, arg1.height);
    }
}

class sortByHeightAndWeight implements Comparator<Person>{  //多要素排序规则重写
    @Override
    public int compare(Person arg0, Person arg1) {
        if (arg0.height - arg1.height == 0)return Double.compare(arg0.weight, arg1.weight);
        return Double.compare(arg0.height, arg1.height);
    }
}

class Person{
    String name;
    String id;
    double height;
    double weight;
    double income;
    public Person(String name, String id, double height, double weight,
                  double income) {
        super();
        this.name = name;
        this.id = id;
        this.height = height;
        this.weight = weight;
        this.income = income;
    }
}

