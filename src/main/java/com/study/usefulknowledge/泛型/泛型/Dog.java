package com.study.usefulknowledge.����.����;

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
        //Java7֮�󣬼��������ǲ���Ҫ��д������
        Dog dog=new Dog<>("28");
        System.out.println(dog.getAge());
    }
}
