package com.study.usefulknowledge;
/**
 *判断小端还是大端规则的方法：
*/
public class 大小头
{
    public static void main(String[] args)
    {
        int x = 1;
        if((char )x=='1' && x ==1)     //取x指针强制转换为char*类型再取值，此时取到的值是int最低字节值
            System.out.printf("little-endian/n");
        else
            System.out.printf("big-endian/n");
    }

}


