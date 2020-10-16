package com.study.usefulknowledge.泛型.泛型;

/**
 * @Autor rongxiaokun
 * @Date 2020/6/7  12:31
 */
public class Dog<T> {
    private T age;

    public Dog(T age) {
        this.age = age;
    }

    public T getAge() {
        return age;
    }

    public static void main(String[] args) {
        //Java7之后，尖括号中是不需要填写参数的
        Dog dog=new Dog<>("28");
        System.out.println(dog.getAge());
    }
}
