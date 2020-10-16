package com.study.usefulknowledge.·ºÐÍ.·ºÐÍ;

/**
 * @Autor rongxiaokun
 * @Date 2020/6/7  12:32
 */
public class TestMethod {
    public static <T> boolean isHas(T[] arr, T elemt){
        for(T t:arr){
            if(t.equals(elemt)){
                return true;
            }
        }
        return false;
    }
    public <S> boolean isString(S s){
        if(s instanceof String){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Integer[] arr={1,5,6,8};
        System.out.println(isHas(arr,8));
        TestMethod testMethod=new TestMethod();
        System.out.println(testMethod.isString(5));
    }
}