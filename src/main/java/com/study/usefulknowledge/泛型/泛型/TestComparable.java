package com.study.usefulknowledge.·ºÐÍ.·ºÐÍ;

/**
 * @Autor rongxiaokun
 * @Date 2020/6/7  12:34
 */
public class TestComparable implements MyComparable<TestComparable>,MyComparablePro{

    private Integer n;

    public TestComparable(int n) {
        this.n = n;
    }

    @Override
    public boolean isEquals(TestComparable testComparable) {
        return this.n.intValue()==testComparable.getN().intValue()?true:false;
    }

    public Integer getN() {
        return n;
    }

    public static void main(String[] args) {
        TestComparable testComparable1=new TestComparable(2);
        TestComparable testComparable2=new TestComparable(2);
        System.out.println(testComparable1.isEquals(testComparable2));
        System.out.println(testComparable1.isEqualsPro(testComparable2));
    }

    @Override
    public <T> boolean isEqualsPro(T t) {
        if(t instanceof TestComparable){
            return this.n.intValue()==((TestComparable)t).getN().intValue()?true:false;}
        return false;
    }
}
interface MyComparable<T> {
    boolean isEquals(T t);
}

interface MyComparablePro {
    <T> boolean isEqualsPro(T t);
}
